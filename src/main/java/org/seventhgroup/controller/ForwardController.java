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
     * @author cloudsoul-ZX
     * 转发到主页
     */
    @RequestMapping("/main")
    public String showMainPage(){
        return "main";
    }
    /**
     * @author cloudsoul-ZX
     * 转发到登录页面
     */
    @RequestMapping("/toLogin")
    public String toLoginPage() {
        return "login";
    }
}