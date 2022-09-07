package com.sw.novel.crawler.domain;

import com.sw.common.to.BookChapterTo;
import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.crawler.feign.BookFeignService;
import com.sw.novel.crawler.source.BiQuGeCrawlerSource;
import com.sw.novel.crawler.source.factroy.CrawlerSourceFactory;
import com.sw.novel.crawler.utils.MyBeanUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author sunao
 * @since 2022/9/6
 * description: 爬虫逻辑代码
 */
@Slf4j
public class MyCrawler extends WebCrawler {

    private static CrawlerSource crawlerSource;

    // 文件后缀
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    // 小说章节索引
    private final static ArrayList chapterIndex = new ArrayList();

    // 当前爬取的小说保存在库中的id
    private static Long bookId;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (href.startsWith("http://www.biqu5200.net/")) {
            crawlerSource = CrawlerSourceFactory.getCrawlerSource("BiQuGe");
        }
        return !FILTERS.matcher(href).matches() // 根据定义的过滤掉我们不需要爬虫的页面
                // 比如这个路径开头 这样才能保证我们获取的章节内容是该小说下的
                && href.startsWith(referringPage.getWebURL().getURL());
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        BookFeignService bookFeignService = MyBeanUtil.getBean(BookFeignService.class);

        if (page.getParseData() instanceof HtmlParseData) { // 判断是否是html数据

            log.info("即将爬取url为: {}的html页面...", url);

            // 加锁：
            // 确保该操作被一个线程执行一次
            // 如果不加锁 在执行添加章节信息的时候可能会导致bookId为null的情况
            if (bookId == null) {
                synchronized (MyCrawler.class) {
                    if (page.getWebURL().getParentUrl() == null) {  // 源种子页面
                        // 获取当前小说的详细信息
                        BookInfoTo bookInfo = crawlerSource.getBookInfo(page, chapterIndex);
                        // 远程调用 添加到表中
                        R r = bookFeignService.saveBook(bookInfo);
                        // 获取添加完成后的小说id
                        bookId = Long.parseLong(r.get("data").toString());
                        // 直接return 源种子路径只需做上述步骤
                        return;
                    }
                }
            }

            // 获取该章节的信息
            BookChapterTo chapterContent = crawlerSource.getChapterContent(page, chapterIndex);
            chapterContent.setBookId(bookId);
            // 远程调用 保存到表中
            bookFeignService.saveChapter(chapterContent);
        }
    }

}
