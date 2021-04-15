package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    //跨域访问 配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/sftp/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
