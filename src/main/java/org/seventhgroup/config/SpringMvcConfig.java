package org.seventhgroup.config;

import org.seventhgroup.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import java.util.List;

@Configuration
@PropertySource("classpath:ignoreUrl.properties")
//等同于<context:component-scan base-package="org.seventhgroup.controller"/>
@ComponentScan({"org.seventhgroup.controller"})
/*@Import({MyWebMvcConfig.class})*/
@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {
  @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;
    @Bean
    public ResourcesInterceptor resourcesInterceptor(){
        return new ResourcesInterceptor(ignoreUrl);
    }
    /*
     * 在注册的拦截器类中添加自定义拦截器
     * addPathPatterns()方法设置拦截的路径
     * excludePathPatterns()方法设置不拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( resourcesInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**");
    }
}