package org.seventhgroup.controller;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;

public class UserController {
    private UserService userService;
    public String login(User user){
        return userService.login(user);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
