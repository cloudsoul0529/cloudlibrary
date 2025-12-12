package org.seventhgroup.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    //加载Root容器配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    //加载Servlet容器配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    //拦截所有请求
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // 配置过滤器，解决中文乱码问题
    @Override
    protected javax.servlet.Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return new javax.servlet.Filter[]{filter};
    }
}