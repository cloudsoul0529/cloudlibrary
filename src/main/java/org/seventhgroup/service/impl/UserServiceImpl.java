package org.seventhgroup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.seventhgroup.dao.UserMapper;
import org.seventhgroup.entity.PageResult;
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
    //注入userMapper
    @Autowired
    private UserMapper userMapper;
    //根据用户邮箱获取盐和哈希值，然后验证
    @Override
    public User login(User user) {
        String salt = userMapper.getSalt(user);
        String hash = userMapper.getHash(user);
        if(SHA256WithSaltUtil.verify(user.getPassword(), salt, hash)){
            return userMapper.login(user);
        }
        return null;
    }

    /**
     * 新增用户
     * @param user 新增的用户信息
     */
    @Override
    public void addUser(User user) {
        //新增的用户 默认状态都设置为0,即注册状态
        user.setStatus(User.ACTIVE);
        //设置盐与哈希
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
     * 根据id办理用户注销
     * @param id 注销用户的id
     */
    @Override
    public void delUser(Integer id) {
        //根据id查询出用户的完整信息
        User user = this.findById(id);
        //设置用户为注销状态
        user.setStatus(User.DELETED);
        //设置当天为用户的注销时间
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
        // 使用分页插件:
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page =  userMapper.searchUsers(user);
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
    public Integer checkName(String name) {
        return userMapper.checkName(name);
    }

    /**
     * 检查用户邮箱是否存储
     * @param email 待检查的用户邮箱
     */
    @Override
    public Integer checkEmail(String email) {
        return userMapper.checkEmail(email);
    }
}
