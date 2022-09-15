package com.sw.novel.book.controller;

import com.sw.common.to.BookChapterTo;
import com.sw.common.utils.R;
import com.sw.novel.book.service.BookChapterService;
import com.sw.novel.book.vo.BookChapterContentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "查询对应的小说章节的内容", notes = "免费章节")
    @GetMapping("/free/content/{id}")
    public R getChapterContent(@PathVariable("id") Long id) {
        try {
            BookChapterContentVo bookChapterContentVo = bookChapterService.getFreeChapterContent(id);
        } catch (IllegalAccessException e) {
           return R.error();
        }
        return R.ok();
    }

}
