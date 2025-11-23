package org.seventhgroup.dao;

import org.seventhgroup.pojo.User;

public interface UserMapper {
    //查询登录用户的用户名密码是否存在
    public String login(User usr);
}
