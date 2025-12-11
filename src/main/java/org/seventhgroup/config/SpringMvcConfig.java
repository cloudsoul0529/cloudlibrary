package org.seventhgroup.config;

import org.seventhgroup.interceptor.ResourcesInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author cloudsoul
 */
@Configuration
//扫描Controller
@ComponentScan("org.seventhgroup.controller")
//开启MVC注解驱动
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    //普通用户允许访问的路径
    private final List<String> allowedUrl = Arrays.asList(
            "/index",
            "/user/logout",
            "/book/selectNewbooks",
            "/book/findById",
            "/book/borrowBook",
            "/book/search",
            "/book/searchBorrowed",
            "/book/returnBook",
            "/record/searchRecords"
    );

    //创建拦截器Bean
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(allowedUrl);
    }

    //配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    //配置静态资源放行
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourcesInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/img/**",
                        "/user/login",
                        "/login",
                        "/"
                );
    }

    //配置首页跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问"/"时，重定向到登录页
        registry.addRedirectViewController("/", "/login");
    }
}