package com.mszlu.blog.config;

import com.mszlu.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 解决跨域问题，允许指定路径访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("/").
                allowedOrigins("http://localhost:8080").
                allowedOrigins("http://localhost:80").
                allowedOrigins("http://47.96.15.35:80").
                allowedOrigins("http://47.96.15.35:8080");
    }

    /**
     * 增加登录拦截器，并指定拦截路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).
                addPathPatterns("/test").
                addPathPatterns("/comments/create/change").
                addPathPatterns("/articles/publish");
    }
}
