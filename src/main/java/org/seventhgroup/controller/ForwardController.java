package org.seventhgroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cloudsoul
 * 负责转发，保持url纯净
 */
@Controller
public class ForwardController {
    /**
     * 转发到管理员主页
     */
    @RequestMapping("/main")
    public String showMain(){
        return "main";
    }
    /**
     * 转发到普通用户主页
     */
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
    /**
     * 转发到登录页面
     */
    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }
}