package com.sw.novel.home.controller;

import com.sw.common.utils.R;
import com.sw.novel.home.service.HomeBookService;
import com.sw.novel.home.vo.HomeBookVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sunao
 * @since 2022/9/7
 * description: 首页小说
 */
@Api(tags = "HomeBookController", description = "首页小说")
@RestController
@RequestMapping("/home/book")
public class HomeBookController {

    @Autowired
    private HomeBookService homeBookService;

    @ApiOperation("获取首页猜你喜欢小说列表")
    @GetMapping("/recommend")
    public R getRecommendBooks() {
        List<HomeBookVo> homeBookVoList = homeBookService.getRecommendBookList();
        return R.ok().setData(homeBookVoList);
    }

}
