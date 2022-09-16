package com.sw.novel.book.controller;

import com.sw.common.utils.R;
import com.sw.novel.book.service.BookChapterService;
import com.sw.novel.book.vo.BookChapterContentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunao
 * @since 2022/9/16
 * description: 该控制器仅对会员用户开放
 */
@Api(tags = "HasVipRoleController", description = "小说会员业务")
@RestController
@RequestMapping("/book/vip")
public class HasVipRoleController {

    @Autowired
    private BookChapterService bookChapterService;

    @ApiOperation(value = "查询对应小说章节内容", notes = "vip章节")
    @GetMapping("/content/{id}")
    public R getChapterContent(@PathVariable("id") Long id) {
        BookChapterContentVo bookChapterContentVo =  bookChapterService.getVipChapterContent(id);
        return R.ok();
    }

}
