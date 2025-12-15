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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String login(User user, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            User storedUser = userService.login(user);
            if (storedUser != null) {
                //存储用户信息
                request.getSession().setAttribute("USER_SESSION", storedUser);
                //根据用户角色跳转至不同页面
                if ("ADMIN".equals(storedUser.getRole())) {
                    return "redirect:/main";
                }
                else{
                    return "redirect:/index";
                }
            }
            //addFlashAttribute把数据暂存在Session中，重定向后的下一次请求取出
            redirectAttributes.addFlashAttribute("msg", "邮箱或密码错误");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "系统错误");
            return "redirect:/login";
        }
    }

    /**
     * @author cloudsoul-ZX
     * 注销登录
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //销毁 Session
        session.invalidate();
        //重定向回登录页
        return "redirect:/login";
    }

    /**
     * @author cloudsoul-ZX
     * 用户注册
     */
    @RequestMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "注册失败！");
            return "redirect:/register";
        }
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
     * 编辑用户
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
     * 编辑信息
     */
    @ResponseBody
    @RequestMapping("/editMyself")
    public Result editMyself(User user) {
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
     * 用户注销
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public Result delUser(Integer id) {
        try {
            return userService.delUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注销失败!");
        }
    }

    /**
     * @author cloudsoul-ZX
     * 用户恢复（管理员）
     */
    @ResponseBody
    @RequestMapping("/recoverUser")
    public Result recoverUser(Integer id) {
        try {
            userService.recoverUser(id);
            return new Result(true, "恢复办理成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "恢复办理失败!");
        }
    }

    /**
     * @author cloudsoul-ZX
     * 查询用户
     */
    @RequestMapping("/search")
    public ModelAndView search(User user, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        //由Controller定义page起始页和条数
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
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    /**
     * @author cloudsoul-ZX
     * 根据id查询用户
     */
    @ResponseBody
    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }

    /**
     * @author cloudsoul-ZX
     * 新增、编辑用户时检查已注册的邮箱是否存在
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