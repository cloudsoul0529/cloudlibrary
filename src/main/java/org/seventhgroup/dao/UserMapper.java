package org.seventhgroup.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.seventhgroup.dto.PasswordResult;
import org.seventhgroup.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author cloudsoul-ZX
 */
@Mapper
@Repository
public interface UserMapper{
    //获取用户盐与哈希值
    PasswordResult getPasswordResult(User user);
    //用户登录
    User login(User user);
    //新增用户
    void addUser(User user);
    //编辑用户信息
    void editUser(User user);
    //搜索用户
    Page<User> searchUsers(User user);
    //根据用户id查询用户信息
    User findById(Integer id);
    //检查用户名是否已经存在
    String checkName(String name);
    //检查用户邮箱是否已经存在
    String checkEmail(String email);
}