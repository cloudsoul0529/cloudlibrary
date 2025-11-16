package org.seventhgroup.dao.Imp;

import org.seventhgroup.dao.UserDao;
import org.seventhgroup.pojo.User;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class UserDaoImp implements UserDao {
    @Override
    public String login(User user){
        System.out.println("根据客户端传递的用户名：" + user.getName() + "，密码：" + user.getPassword());
        return "登陆成功";
    }
}
