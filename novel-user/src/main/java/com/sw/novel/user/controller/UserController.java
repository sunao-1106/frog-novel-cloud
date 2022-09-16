package com.sw.novel.user.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.common.utils.R;
import com.sw.novel.user.entity.UserEntity;
import com.sw.novel.user.service.UploadService;
import com.sw.novel.user.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author makejava
 * @since 2022-09-06 16:09:39
 */

@Api(tags = "UserController",description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UploadService uploadService;


    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @RequestMapping("/test02")
    public R test02() {
        return R.ok();
    }


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UserEntity> page, UserEntity user) {
        return R.ok();
    }

    /**
     * 头像上传到 oss(七牛云)
     */
    @RequestMapping("/upload")
    public R Upload(MultipartFile img){
        return uploadService.uploadImg(img);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/outLogin")
    public com.sw.common.utils.R outLogin(){
        return userService.outLogin();
    }

    /**
     * 用户注册
     * @return
     */
    @PostMapping("/Register")
    public R Register(@RequestBody UserEntity user){
        return userService.Register(user);
    }

    /**
     * 用户信息
     */
    @GetMapping("/UserInfo")
    public R UserInfo(){
        return userService.UserInfo();
    }

    /**
     * 用户浏览记录
     */
    @GetMapping("/bookHistory")
    public R bookHistory(){
        return userService.bookHistory();
    }

    /**
     *权限测试
     * @return
     */
    @RequestMapping("/test")
    @PreAuthorize("hasAuthority('abc')")
    public String Test(){
        return "超级管理员";
    }

}

