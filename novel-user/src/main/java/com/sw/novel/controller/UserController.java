package com.sw.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.common.utils.R;
import com.sw.novel.entity.User;
import com.sw.novel.entity.vo.UserVo;
import com.sw.novel.service.UploadService;
import com.sw.novel.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author makejava
 * @since 2022-09-06 16:09:39
 */

@Api(tags = "UserController",description = "用户接口")
@RestController
@RequestMapping("user")
public class UserController extends ApiController {

    @Autowired
    UploadService uploadService;




    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */

    @PostMapping("/login")
    public com.sw.common.utils.R Login(
            @RequestBody User user
    ){
        return userService.login(user);
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
    public R Register(@RequestBody User user){
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

