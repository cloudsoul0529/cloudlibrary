package org.seventhgroup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.seventhgroup.dao.UserMapper;
import org.seventhgroup.dto.PasswordResult;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.seventhgroup.util.SHA256WithSaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @author cloudsoul-ZX
     * 用户登录
     */
    @Override
    public User login(User user) {
        // 获取盐和哈希值
        PasswordResult passwordResult = userMapper.getPasswordResult(user);

        // 【新增安全判断】如果查不到用户（passwordResult为null），直接返回null，防止报空指针异常
        if (passwordResult == null) {
            return null;
        }

        String salt = passwordResult.getPasswordSalt();
        String hash = passwordResult.getPasswordHash();

        // 验证成功则登录
        // 注意：这里要确保 salt 和 hash 也不为空，虽然正常数据不会为空
        if (salt != null && hash != null && SHA256WithSaltUtil.verify(user.getPassword(), salt, hash)) {
            return userMapper.login(user);
        }
        return null;
    }

    /**
     * @author cloudsoul-ZX
     * 新增用户（管理员）
     */
    @Override
    public void addUser(User user) {
        user.setStatus(User.ACTIVE); // 确保这里引用的是 User 类里的常量，或者直接写 "0"
        // 设置盐，计算哈希值
        try {
            byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
            String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(), salt);
            String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);
            user.setPasswordSalt(storedSalt);
            user.setPasswordHash(hash);
            userMapper.addUser(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author cloudsoul-ZX
     * 编辑用户
     * 【重点修改】：增加了密码判空逻辑，防止改角色时把密码清空
     */
    @Override
    public void editUser(User user) {
        try {
            // 1. 判断是否需要修改密码
            // 只有当传入的密码不为 null 且不为空字符串时，才重新生成哈希
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
                String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(), salt);
                String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);

                user.setPasswordSalt(storedSalt);
                user.setPasswordHash(hash);
            } else {
                // 2. 如果密码为空（只改了角色或其他信息）
                // 显式设置为 null，让 UserMapper.xml 中的 <if test="..."> 跳过这两个字段的更新
                user.setPasswordSalt(null);
                user.setPasswordHash(null);
            }

            userMapper.editUser(user);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author cloudsoul-ZX
     * 用户注销（实质编辑用户）
     * 【重点修改】：注销时修改用户名和邮箱，释放唯一索引
     */
    @Override
    public void delUser(Integer id) {
        // 1. 先查出这个用户
        User user = this.findById(id);

        // 2. 状态改为已注销 (假设 User.DELETED 是 "1")
        user.setStatus(User.DELETED);

        // 3. 设置注销时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setDeletedate(dateFormat.format(new Date()));

        // 4. 【关键步骤】给用户名和邮箱加上时间戳后缀，避免占用名额
        // 这样下次注册同名用户时，就不会报 "Duplicate entry" 错误了
        long timestamp = System.currentTimeMillis();
        user.setName(user.getName() + "_del_" + timestamp);
        user.setEmail(user.getEmail() + "_del_" + timestamp);

        // 5. 确保密码字段为null，防止注销时把密码给改乱了（虽然注销了也没人登，但保持数据干净）
        user.setPasswordSalt(null);
        user.setPasswordHash(null);

        userMapper.editUser(user);
    }

    /**
     * @author cloudsoul-ZX
     * 搜索用户
     */
    @Override
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = userMapper.searchUsers(user);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @author cloudsoul-ZX
     * 根据id查询用户
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * @author cloudsoul-ZX
     * 检查用户名
     */
    @Override
    public boolean checkName(String name) {
        return userMapper.checkName(name) != null;
    }

    /**
     * @author cloudsoul-ZX
     * 检查邮箱
     */
    @Override
    public boolean checkEmail(String email) {
        return userMapper.checkEmail(email) != null;
    }
}