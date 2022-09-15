package com.sw.novel.book.controller;

import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.book.entity.BookInfoEntity;
import com.sw.novel.book.service.BookInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sunao
 * @since 2022/9/7
 * description: 小说业务
 */
@Api(tags = "BookInfoController", description = "小说业务")
@RestController
@RequestMapping("/book/info")
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("添加小说")
    @PostMapping("/saveBook")
    public R saveBook(@RequestBody BookInfoTo bookInfoTo) {
        Long bookId = bookInfoService.saveBookInfo(bookInfoTo);
        return R.ok().setData(bookId);
    }

    @ApiOperation("通过小说id查询小说详细信息")
    @GetMapping("/get/{id}")
    public R getBookInfoById(@PathVariable("id") Long id) {
        BookInfoTo bookInfo = bookInfoService.getBookById(id);
        return R.ok().setData(bookInfo);
    }

    @ApiOperation(value = "增加小说浏览量")
    @PutMapping("/add/{id}")
    public R addViewCount(@PathVariable("id") Long id) {
        rabbitTemplate.convertAndSend("book.add.view.exchange", "book.clicked", id);
        return R.ok();
    }

}
