package com.sw.novel.book.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunao
 * @since 2022/9/6
 * description:
 */
@Api(tags = "BookController", description = "小说业务")
@RestController
public class BookController {

    @ApiOperation("测试swagger")
    @GetMapping("test")
    public String test() {
        return "success";
    }


}
