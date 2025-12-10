package org.seventhgroup.config;

import org.seventhgroup.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import java.util.List;

@Configuration
@PropertySource("classpath:ignoreUrl.properties")
@ComponentScan({"org.seventhgroup.controller"})
@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {
    //将字符串按逗号分割存入List<String>
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;
    //创建拦截器示例
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl);
    }

    //registry.addInterceptor(resourcesInterceptor())注册拦截器
    //.addPathPatterns("/**")设置拦截的路径模式
    //excludePathPatterns("/css/**","/js/**","/img/**")设置排除的路径模式
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourcesInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**","/js/**","/img/**",
                        "/views/login.jsp"
                );
    }
}