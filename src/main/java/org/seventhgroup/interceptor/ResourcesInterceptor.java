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

/**
 * @author cloudsoul
 */
public class ResourcesInterceptor extends HandlerInterceptorAdapter {
    //普通用户允许访问的路径
    private List<String> allowedUrl;
    public ResourcesInterceptor(List<String> allowedUrl) {
        this.allowedUrl = allowedUrl;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //禁用页面缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        User user = (User) request.getSession().getAttribute("USER_SESSION");
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
        else{
            //剥离URI中的项目名
            String uri = request.getRequestURI();
            System.out.println(">>>>>>>>>>>uri+" + uri);
            System.out.println(">>>>>>>>>>>uri+" + uri);
            System.out.println(">>>>>>>>>>>uri+" + uri);
            String contextPath = request.getContextPath();
            String pureUri = uri.substring(contextPath.length());
            System.out.println(">>>>>>>>>>>uri+" + pureUri);
            System.out.println(">>>>>>>>>>>uri+" + pureUri);
            System.out.println(">>>>>>>>>>>uri+" + pureUri);
            if (pureUri.isEmpty()) {
                pureUri = "/";
            }
            for (String allowedUrl : this.allowedUrl) {
                if (pureUri.startsWith(allowedUrl)) {
                    return true;
                }
            }
            //其余情跳转至登录页面
            redirectToLogin(request, response, "您没有权限访问该页面！");
            return false;
        }
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
        response.sendRedirect(request.getContextPath() + "/login");
    }
}