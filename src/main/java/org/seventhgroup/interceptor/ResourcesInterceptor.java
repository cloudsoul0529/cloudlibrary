package org.seventhgroup.interceptor;

import org.seventhgroup.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResourcesInterceptor extends HandlerInterceptorAdapter {
    //任意角色都能访问的路径
    private List<String> ignoreUrl;
    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        String uri = request.getRequestURI();

        //检查是否是登录相关请求
        if (uri.contains("/login")) {
            return true;
        }

        //如果用户未登录，跳转到登录页
        if (user == null) {
            request.setAttribute("msg", "您还没有登录，请先登录！");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return false;
        }

        //管理员可以访问所有页面
        if ("ADMIN".equals(user.getRole())) {
            return true;
        }

        //普通用户只能访问允许的页面
        if ("USER".equals(user.getRole())) {
            for (String allowedUrl : ignoreUrl) {
                if (uri.contains(allowedUrl)) {
                    return true;
                }
            }
            //其余情跳转至登录页面
            request.setAttribute("msg", "您没有权限访问该页面！");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return false;
        }
        return false;
    }
}