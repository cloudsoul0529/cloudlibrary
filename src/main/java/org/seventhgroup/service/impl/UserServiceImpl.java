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
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @author cloudsoul-ZX
     * 用户登录
     */
    //根据用户邮箱获取盐和哈希值，然后验证
    @Override
    public User login(User user) {
        //获取盐和哈希值
        PasswordResult passwordResult = userMapper.getPasswordResult(user);
        String salt = passwordResult.getPasswordSalt();
        String hash = passwordResult.getPasswordHash();
        //验证成功则登录
        if(SHA256WithSaltUtil.verify(user.getPassword(), salt, hash)){
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
        user.setStatus(User.ACTIVE);
        //设置盐，计算哈希值
        try {
            byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
            String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(),salt);
            String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);
            user.setPasswordSalt(storedSalt);
            user.setPasswordHash(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        userMapper.addUser(user);
    }

    /**
     * @author cloudsoul-ZX
     * 用户注销
     */
    @Override
    public void delUser(Integer id) {
        User user = this.findById(id);
        user.setStatus(User.DELETED);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setDeletedate(dateFormat.format(new Date()));
        userMapper.editUser(user);
    }

    /**
     * 编辑用户信息
     * @param user 更新之后的用户信息
     */
    @Override
    public void editUser(User user) {
        userMapper.editUser(user);
    }

    /**
     * 搜索用户
     * @param user 搜索的条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return
     */
    @Override
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = userMapper.searchUsers(user);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 检查用户名是否已经存在
     * @param name 待检查的用户名
     */
    @Override
    public boolean checkName(String name) {
        if (userMapper.checkName(name) != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查用户邮箱是否存储
     * @param email 待检查的用户邮箱
     */
    @Override
    public boolean checkEmail(String email) {
        if (userMapper.checkEmail(email) != null) {
            return true;
        }
        return false;
    }
}
