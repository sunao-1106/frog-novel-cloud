package com.sw.novel.crawler;

import com.sw.novel.crawler.source.BiQuGeCrawlerSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NovelCrawlerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getBooksFromHomePageTest() {
        BiQuGeCrawlerSource biQuGeCrawlerSource = new BiQuGeCrawlerSource();
        biQuGeCrawlerSource.getBooksFromHomePage("http://www.biqu5200.net/");
    }

}
