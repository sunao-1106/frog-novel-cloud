package com.sw.novel.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.sw.novel.crawler.feign")   // 开启feign
public class NovelCrawlerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NovelCrawlerApplication.class, args);
    }

}
