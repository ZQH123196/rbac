package com.example.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zqh
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"com.example.rbac.**"})
public class StartApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    /**
     * 处理 servelet，当打成 war 使用外部容器时使用
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApplication.class);
    }
}
