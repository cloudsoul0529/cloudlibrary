package org.seventhgroup.controller;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.springframework.stereotype.Controller;

/**
 * 负责处理客户端提交的用户相关的请求处理
 * 承担控制器的角色
 *
 * */
@Controller(value = "userController")
public class UserController {
    //调用UserService来进行业务逻辑的处理
    private UserService userService;
    public String login(User user){
        // 调用service(业务处理层)来处理用户登录的请求
        return userService.login(user);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
