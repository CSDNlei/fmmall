package com.fengmi.config;

import com.fengmi.interceptor.CheckTokenInceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
       @Autowired
    private CheckTokenInceptor checkTokenInceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInceptor)
                .addPathPatterns("/shopcart/**")
                .addPathPatterns("/orders/**")
//                .addPathPatterns("")
                .excludePathPatterns("/user/**")
                .addPathPatterns("/user/check");

    }
}
