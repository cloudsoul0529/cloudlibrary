package org.seventhgroup.service.imp;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImp implements UserService {
    @Override
    public String login(User user){
        return "登陆成功";
    }
}
