package org.seventhgroup.service;

import org.seventhgroup.dto.PageResult;
import org.seventhgroup.dto.Result;
import org.seventhgroup.pojo.User;

/**
 * @author cloudsoul-ZX
 */
public interface UserService{
    //通过User的用户账号和用户密码查询用户信息
    User login(User user);
    //添加用户
    void addUser(User user);
    //用户注销
    Result delUser(Integer id);
    //恢复注销
    void recoverUser(Integer id);
    //编辑用户
    void editUser(User user);
    //搜索用户
    PageResult searchUsers(User user, Integer pageNum, Integer pageSize);
    //根据id查询用户信息
    User findById(Integer id);
    //检查已注册的邮箱是否存在
    boolean checkEmail(String email);
}