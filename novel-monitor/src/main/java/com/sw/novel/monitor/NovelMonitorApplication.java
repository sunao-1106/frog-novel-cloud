package com.sw.novel.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan("com.sw.novel")
@MapperScan("com.sw.novel.dao")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NovelMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelMonitorApplication.class, args);
    }

}
