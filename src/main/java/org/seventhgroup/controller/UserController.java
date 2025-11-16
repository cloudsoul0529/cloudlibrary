package org.seventhgroup.controller;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author cloudsoul-ZX
 * @time 2025.11.16
 * */
@Controller(value = "userController")
public class UserController {
    //利用注解注入userService
    @Resource(name = "userService")
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //用户登录
    public String login(User user){
        // 调用service(业务处理层)来处理用户登录的请求
        return userService.login(user);
    }
    //用户注销
    public void logout(){

    }


}
