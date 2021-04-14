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
        //仅仅允许来自 domain2.com 的跨域访问，并且限定访问路径为／api 、方法是 POST 或者 GET 。
        registry.addMapping("/sftp/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
