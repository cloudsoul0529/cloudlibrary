package org.seventhgroup.service.imp;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;

public class UserServiceImp implements UserService {
    @Override
    public String login(User user){
        return "登陆成功";
    }
}
