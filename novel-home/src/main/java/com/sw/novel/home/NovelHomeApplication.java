package com.sw.novel.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling // 开启定时任务
@MapperScan("com.sw.novel.home.dao")
public class NovelHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelHomeApplication.class, args);
    }

}
