package org.seventhgroup.pojo;

import org.seventhgroup.controller.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User)applicationContext.getBean("user");
        System.out.println(user);
        UserController userController= (UserController)applicationContext.getBean("userController");
        String result = userController.login(user);
        System.out.println(result);
    }
}