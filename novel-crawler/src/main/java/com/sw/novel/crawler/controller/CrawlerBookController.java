package com.sw.novel.crawler.controller;

import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.crawler.domain.MyCrawler;
import com.sw.novel.crawler.feign.BookFeignService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sunao
 * @since 2022/9/7
 * description: 小说爬虫业务
 */
@Api(tags = "CrawlerBookController", description = "小说爬虫业务")
@RestController
@RequestMapping("/crawler")
public class CrawlerBookController {

    /**
     * 爬取指定小说的所有章节内容
     * @param url 小说详细页url
     */
    @PostMapping("/start")
    public R startCrawler(@RequestParam("url") String url) {
        String crawlStorageFolder = "S:/crawler";// 定义爬虫数据存储位置

        // 定义了10个爬虫，也就是10个线程
        int numberOfCrawlers = 10;

        CrawlConfig config = new CrawlConfig();// 定义爬虫配置
        config.setCrawlStorageFolder(crawlStorageFolder);// 设置爬虫文件存储位置

        /*
         * 实例化爬虫控制器。
         */
        PageFetcher pageFetcher = new PageFetcher(config);// 实例化页面获取器
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();// 实例化爬虫机器人配置
        // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        // 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        // 实例化爬虫控制器
        CrawlController controller = null;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 种子页面
        controller.addSeed(url);

        // 启动爬虫
        controller.start(MyCrawler.class, numberOfCrawlers);
        return R.ok();
    }

}
