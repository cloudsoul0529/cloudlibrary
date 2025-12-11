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

        // 1. 未登录拦截
        if (user == null) {
            // 放行登录页面的请求，防止死循环
            if (uri.contains("/login") || uri.contains("/toLogin")) {
                return true;
            }
            redirectToLogin(request, response, "您还没有登录，请先登录！");
            return false;
        }

        // 2. 管理员放行
        if ("ADMIN".equals(user.getRole())) {
            return true;
        }

        // 3. 普通用户放行逻辑 【这里是修改重点！】
        if ("USER".equals(user.getRole())) {
            // A. 先放行核心业务页面
            if (uri.contains("/main") ||          // 主页
                    uri.contains("/book/") ||         // 图书模块
                    uri.contains("/record/") ||       // 借阅记录模块
                    uri.contains("/user/logout")) {   // 注销
                return true;
            }

            // B. 再放行静态资源 (css, js, img)
            for (String allowedUrl : ignoreUrl) {
                if (uri.contains(allowedUrl)) {
                    return true;
                }
            }

            // C. 只有访问了不该访问的（比如 /user/addUser），才拦截
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