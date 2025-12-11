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

/**
 * @author cloudsoul
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
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
            userMapper.addUser(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编辑用户
     */
    @Override
    public void editUser(User user) {
        try {
            if (user.getPassword() != null && user.getPassword() != "") {
                byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
                String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(),salt);
                String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);
                user.setPasswordSalt(storedSalt);
                user.setPasswordHash(hash);
            }
            userMapper.editUser(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户注销（实质编辑用户）
     */
    @Override
    public void delUser(Integer id) {
        //添加注销时间及状态改为已注销，用户数据仍保存在数据库中
        User user = this.findById(id);
        user.setStatus(User.DELETED);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setDeletedate(dateFormat.format(new Date()));
        userMapper.editUser(user);
    }

    /**
     * 恢复注销（实质编辑用户）
     */
    @Override
    public void recoverUser(Integer id) {
        //删除注销时间及状态改为已注册
        User user = this.findById(id);
        user.setStatus(User.ACTIVE);
        user.setDeletedate("cancel");
        userMapper.editUser(user);
    }

    /**
     * 搜索用户
     */
    @Override
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //SQL语句被插件改写，返回pageSize条数据，Page包装
        Page<User> page = userMapper.searchUsers(user);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 新增、编辑用户时检查已注册的用户名是否存在
     */
    @Override
    public boolean checkName(String name) {
        if (userMapper.checkName(name) != null) {
            return true;
        }
        return false;
    }

    /**
     * 新增、编辑用户时检查已注册的邮箱是否存在
     */
    @Override
    public boolean checkEmail(String email) {
        if (userMapper.checkEmail(email) != null) {
            return true;
        }
        return false;
    }
}
