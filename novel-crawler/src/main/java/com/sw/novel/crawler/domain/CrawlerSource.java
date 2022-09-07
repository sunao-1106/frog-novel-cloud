package com.sw.novel.crawler.domain;

import com.sw.common.to.BookChapterTo;
import com.sw.common.to.BookInfoTo;
import edu.uci.ics.crawler4j.crawler.Page;

import java.util.List;

/**
 * @author sunao
 * @since 2022/9/6
 * description: 不同小说网站的爬虫源
 */
public interface CrawlerSource {

    /**
     * 获取该小说网站首页中的所有小说详细页的url地址
     * @param homePageUrl 小说网站首页的url地址
     * @return 小说详细页的url地址
     */
    List<String> getBooksFromHomePage(String homePageUrl);

    /**
     * 从小说详细页面中获取小说信息
     * @param page 详细小说页
     * @param chapterIndex 当前小说的章节索引
     */
    BookInfoTo getBookInfo(Page page, List<String> chapterIndex);

    /**
     * 获取小说章节内容
     * @param page 章节页
     * @param chapterIndex 当前小说的章节索引
     */
    BookChapterTo getChapterContent(Page page, List<String> chapterIndex);
}
