package com.sw.novel.crawler.source;

import com.sw.common.to.BookChapterTo;
import com.sw.common.to.BookContentTo;
import com.sw.common.to.BookInfoTo;
import com.sw.novel.crawler.domain.CrawlerSource;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunao
 * @since 2022/9/6
 * description: 笔趣阁网站
 */
@Slf4j
@Component
public class BiQuGeCrawlerSource implements CrawlerSource {

    /**
     * TODO 根据小说的url地址获取该网站可以爬的小说详细页url
     * @param homePageUrl 小说网站首页的url地址
     * @return
     */
    @Override
    public List<String> getBooksFromHomePage(String homePageUrl) {
        // 连接源地址
        Connection connect = Jsoup.connect(homePageUrl);
        Document document = null;
        try {
            // 解析源地址html页面
            document = connect.get();
            log.info(document.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null) {
            // 获取页面中的超链接
            Elements elements = document.getElementsByTag("a");
            // 每个超链接的href值
            List<String> hrefList = elements.eachAttr("href");

//            hrefList.stream().filter(item -> {
//                // 不以html结尾
//                // start with :http://www.biqu5200.net/
//                int begin = item.indexOf("http://www.biqu5200.net/");
//                if (begin < 0) {    // 非http://www.biqu5200.net/开头的url直接过滤
//                    return false;
//                }
//                item.substring(begin);
//                return false;
//            });
            for (String href : hrefList) {
                int begin = href.indexOf("http://www.biqu5200.net/");
                int i = href.lastIndexOf("http://www.biqu5200.net/");
                if (begin < 0) {    // 非http://www.biqu5200.net/开头的url直接过滤
                    continue;
                }
                href.substring(begin);
            }
        }
        return null;
    }

    @Override
    public BookInfoTo getBookInfo(Page page, List<String> chapterIndex) {
        Document document = Jsoup.parse(((HtmlParseData) page.getParseData()).getHtml());
        // 获取list div
        Element list = document.getElementById("list");
        // 获取list div下的dd标签
        Elements dd = list.getElementsByTag("dd");
        // 获取dd标签下的a标签
        for (Element element : dd) {
            Elements a = element.getElementsByTag("a");
            String href = a.first().attr("href");
            // 将a标签中的href属性value值存入list中
            chapterIndex.add(href);
        }

        BookInfoTo bookInfoTo = new BookInfoTo();
        // 获取小说书名
        Element info = document.getElementById("info");
        Element bookName = info.getElementsByTag("h1").first();

        // 获取小说作者
        Element author = info.getElementsByTag("p").first();

        // 获取小说介绍
        StringBuilder sb = new StringBuilder();
        Element intro = document.getElementById("intro");
        Elements p = intro.getElementsByTag("p");
        for (Element element : p) {
            // 添加分隔号 方便后续截串
            sb.append(element.toString() + "/");
        }

        // 获取小说封面图片
        Element bookImage = document.getElementById("fmimg").getElementsByTag("img").first();
        // 图片src
        String src = bookImage.attr("src");

        // 封装信息
        bookInfoTo.setBookName(bookName.text());
        bookInfoTo.setAuthorName(author.text());
        bookInfoTo.setDescription(sb.toString());
        bookInfoTo.setImage(src);
        return bookInfoTo;
    }

    @Override
    public BookChapterTo getChapterContent(Page page, List<String> chapterIndex) {
        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        Document document = Jsoup.parse(htmlParseData.getHtml());
        // 获取章节标题
        BookChapterTo bookChapterTo = new BookChapterTo();
        Element chapterName = document.getElementsByClass("bookname").first();
        Element h1 = chapterName.getElementsByTag("h1").first();
        bookChapterTo.setChapterName(h1.text());

        // 获取该章节的索引
        bookChapterTo.setChapterIndex(chapterIndex.indexOf(page.getWebURL().getURL()));

        // 获取章节内容
        List<String> bookContent = new ArrayList<String>();
        long wordCount = 0L;
        Element content = document.getElementById("content");
        Elements p = content.getElementsByTag("p");
        for (Element element : p) {
            String text = element.text();
            // 计算该章节字数
            wordCount += text.length();
            // 添加分隔符 方便后续截串
            bookContent.add(text + "/");
        }
        bookChapterTo.setWordCount(wordCount);

        // 封装章节内容
        BookContentTo bookContentTo = new BookContentTo();
        bookContentTo.setChapterContent(bookContent.toString());
        bookChapterTo.setBookContentTo(bookContentTo);
        return bookChapterTo;
    }

}
