package org.seventhgroup.interceptor;

import org.seventhgroup.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.RequestContextUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        //登录请求在注册拦截器时无视
        //如果用户未登录，跳转到登录页
        if (user == null) {
            redirectToLogin(request, response, "您还没有登录，请先登录！");
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
            redirectToLogin(request, response, "您没有权限访问该页面！");
            return false;
        }
        return false;
    }

    //FlashMap把数据暂存在Session中，重定向后取出
    public void redirectToLogin(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
        FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
        if (flashMapManager != null) {
            FlashMap flashMap = new FlashMap();
            flashMap.put("msg", msg);
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
        }
        //重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/toLogin");
    }
}