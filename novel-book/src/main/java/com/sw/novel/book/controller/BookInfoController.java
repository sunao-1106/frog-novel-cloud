package com.sw.novel.book.controller;

import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.book.service.BookInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sunao
 * @since 2022/9/7
 * description:
 */
@Api(tags = "BookInfoController", description = "小说业务")
@RestController
@RequestMapping("book/info")
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @PostMapping("/saveBook")
    public R saveBook(@RequestBody BookInfoTo bookInfoTo) {
        Long bookId = bookInfoService.saveBookInfo(bookInfoTo);
        return R.ok().setData(bookId);
    }

}
