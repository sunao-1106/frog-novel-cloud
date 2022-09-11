package com.sw.novel.book.controller;

import com.sw.common.to.BookChapterTo;
import com.sw.common.utils.R;
import com.sw.novel.book.service.BookChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunao
 * @since 2022/9/7
 * description: 小说章节业务
 */
@Api(tags = "BookChapterController", description = "小说章节业务")
@RestController
@RequestMapping("/book/chapter")
public class BookChapterController {

    @Autowired
    private BookChapterService bookChapterService;

    @ApiOperation("保存小说章节信息")
    @PostMapping("/saveChapter")
    public R saveChapter(@RequestBody BookChapterTo chapterTo) {
        bookChapterService.saveChapter(chapterTo);
        return R.ok();
    }

}
