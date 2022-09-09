package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.common.utils.JwtUtil;
import com.sw.common.utils.R;
import com.sw.novel.config.RedisCache;
import com.sw.novel.config.LoginUser;
import com.sw.novel.dao.UserDao;
import com.sw.novel.entity.User;
import com.sw.novel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    /**
     * 登录接口实现
     * @param user
     * @return R
     */
    @Override
    public R login(User user) {
        //用户名密码方式进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        Object jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        return R.ok(map);
    }
}

