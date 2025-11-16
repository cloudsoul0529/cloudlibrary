package org.seventhgroup.service.imp;

import org.seventhgroup.dao.UserDao;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "userService")
public class UserServiceImp implements UserService {

    @Resource(name = "userDao")
    UserDao userDao;
    @Override
    public String login(User user){
        return userDao.login(user);
    }
}
