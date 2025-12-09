package org.seventhgroup.controller;

import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cloudsoul-ZX
 * 用户登录、查询、编辑、新增、注销
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @author cloudsoul-ZX
     * 用户登录
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            User u = userService.login(user);
            if (u != null) {
                //存储用户信息
                request.getSession().setAttribute("USER_SESSION", u);
                //根据角色跳转至不同页面
                String role = u.getRole();
                if ("ADMIN".equals(role)) {
                    return "redirect:/views/main.jsp";
                } else {
                    return "redirect:/views/index.jsp";
                }
            }
            request.setAttribute("msg", "用户名或密码错误");
            return "forward:/views/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/views/login.jsp";
        }
    }

    /**
     * @author cloudsoul-ZX
     * 注销登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        //销毁Session，清除数据并跳转回登陆页面
        request.getSession().invalidate();
        return "forward:/views/login.jsp";
    }

    /**
     * @author cloudsoul-ZX
     * 新增用户（管理员）
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public Result addUser(User user) {
        try {
            userService.addUser(user);
            return new Result(true, "新增成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败!");
        }
    }

    /**
     * @author cloudsoul-ZX
     * 用户注销
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public Result delUser(Integer id) {
        try {
            userService.delUser(id);
            return new Result(true, "注销办理成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注销办理失败!");
        }
    }

    /**
     * @author cloudsoul-ZX
     * 编辑用户（管理员）
     */
    @ResponseBody
    @RequestMapping("/editUser")
    public Result editUser(User user) {
        try {
            userService.editUser(user);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * @author cloudsoul-ZX
     * 查询用户
     */
    @RequestMapping("/search")
    public ModelAndView search(User user, Integer pageNum, Integer pageSize) {
        //由Controller定义page
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = userService.searchUsers(user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", user);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", "/user/search");
        return modelAndView;
    }

    /**
     * 根据id查询用户
     * @param id 用户id，用作查询条件
     */
    @ResponseBody
    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }

    /**
     * 检查用户名称是否已经存在
     * @param name 用户名称
     */
    @ResponseBody
    @RequestMapping("/checkName")
    public Result checkName(String name) {
        boolean result = userService.checkName(name);
        if (result) {
            return new Result(false, "名字重复!");
        } else {
            return new Result(true, "名字可用!");
        }
    }

    /**
     * 校验用户的邮箱是否已经存在
     * @param email 被校验的用户邮箱
     */
    @ResponseBody
    @RequestMapping("/checkEmail")
    public Result checkEmail(String email) {
        boolean result = userService.checkEmail(email);
        if (result) {
            return new Result(false, "邮箱重复!");
        } else {
            return new Result(true, "邮箱可用!");
        }
    }
}