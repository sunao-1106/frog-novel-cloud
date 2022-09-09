package com.sw.novel.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan("com.sw.novel")
public class NovelUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelUserApplication.class, args);
    }

}
