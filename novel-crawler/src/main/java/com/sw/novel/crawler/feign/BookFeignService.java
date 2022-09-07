package com.sw.novel.crawler.feign;

import com.sw.common.to.BookChapterTo;
import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author sunao
 * @since 2022/9/7
 * description:
 */
@FeignClient("novel-book")
public interface BookFeignService {

    /**
     * 添加小说
     */
    @PostMapping("book/info/saveBook")
    R saveBook(@RequestBody BookInfoTo bookInfoTo);

    /**
     * 保存小说章节信息
     */
    @PostMapping("book/chapter/saveChapter")
    R saveChapter(@RequestBody BookChapterTo chapterTo);
}
