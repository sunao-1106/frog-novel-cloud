package com.sw.novel.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunao
 * @since 2022/9/6
 * description:
 */
@Api(tags = "UserController", description = "用户模块业务")
@RestController
public class UserController {

    @ApiOperation("测试swagger")
    @GetMapping("test")
    public String test() {
        return "success";
    }

}
