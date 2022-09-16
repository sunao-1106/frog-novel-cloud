package com.sw.novel.user.controller;

import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.R;
import com.sw.novel.user.dao.UserMapper;
import com.sw.novel.user.entity.UserEntity;
import com.sw.novel.user.exception.AuthenticateException;
import com.sw.novel.user.exception.UserNotFoundException;
import com.sw.novel.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author sunao
 * @since 2022/9/16
 * description: 用户登录认证及注册
 */
@Api(tags = "UserAuthController", description = "用户登录认证及注册")
@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录功能")
    @PostMapping("/login")
    public R login(@RequestBody UserEntity user) {
        String token = null;
        try {
            token = userService.login(user);
        } catch (BadCredentialsException e) { // 用户名或密码错误
            return R.error(BizCodeEnum.USER_PASSWORD.getCode(), BizCodeEnum.USER_PASSWORD.getMessage());
        } catch (InternalAuthenticationServiceException e) { // 用户名不存在，提示用户名或密码错误
            return R.error(BizCodeEnum.USER_PASSWORD.getCode(), BizCodeEnum.USER_PASSWORD.getMessage());
        }
        return R.ok().put("token", token);
    }

}
