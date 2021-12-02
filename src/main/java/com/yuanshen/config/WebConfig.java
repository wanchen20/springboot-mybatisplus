package com.yuanshen.config;

import com.yuanshen.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/12 - 10:27
 * @Description:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //在LoginInterceptor拦截器中注入service无效，此法可以解决该问题
    @Bean
    public LoginInterceptor getInstance(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getInstance());
        registration
                .addPathPatterns("/**") //所有路径都被拦截
                .excludePathPatterns("/api/user/login") //添加不拦截路径
                .excludePathPatterns("/swagger-resources/**","/webjars/**","/v2/**","/swagger-ui.html/**");
    }
}
