package com.sw.novel.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.sw.novel.home.dao")
public class NovelHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelHomeApplication.class, args);
    }

}
