package org.seventhgroup.config;

import org.seventhgroup.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import java.util.List;

@Configuration
//扫描Controller
@ComponentScan("org.seventhgroup.controller")
//读取配置文件
@PropertySource("classpath:ignoreUrl.properties")
//开启MVC注解驱动
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;

    //创建拦截器Bean
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl);
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
                        "/toLogin",
                        "/"
                );
    }

    //配置首页跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问"/"时，重定向到登录页
        registry.addRedirectViewController("/", "/toLogin");
    }
}