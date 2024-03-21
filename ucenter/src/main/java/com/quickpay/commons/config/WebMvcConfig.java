package com.quickpay.commons.config;

import com.quickpay.business.interceptor.ClientIpInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor clientIpInterceptor(){
        return new ClientIpInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //intercept all requests
        registry.addInterceptor(clientIpInterceptor())
                .addPathPatterns("/**");
          //todo exclude open doc api
    }
}
