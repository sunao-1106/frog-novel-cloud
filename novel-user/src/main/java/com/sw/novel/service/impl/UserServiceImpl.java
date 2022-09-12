package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.AppException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.common.Either;

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

    //退出登录接口
    @Override
    public R outLogin() {
        //获取SecurityContextHolder中的认证信息，删除redis中对应的数据即可。
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("================================="+principal);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //没有登录 默认数据为  anonymousUser  会出现String  == 》 LoginUser 异常
        //获取 用户id
        Long id = loginUser.getUser().getId();
        //删除 redis 中用户的信息
        redisCache.deleteObject("login:"+id);
        return R.ok("退出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public R Register(User user) {
        //对密码经行加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                //再把密码后的密码放入User
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //保存成功
        if (save(user)) {
            return R.ok(BizCodeEnum.USER_REGISTER.getMessage());
        }else {
            throw new AppException(BizCodeEnum.USER_REGISTER_ERROR);
        }
    }
}

