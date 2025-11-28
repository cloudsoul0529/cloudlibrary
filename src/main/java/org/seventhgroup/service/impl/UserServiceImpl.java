package org.seventhgroup.service.impl;

import org.seventhgroup.dao.UserMapper;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "userService")
public class UserServiceImpl implements UserService {


    @Resource(name = "userDao")
    UserMapper userMapper;
    @Override
    public String login(User user){
        return userMapper.login(user);
    }
}
