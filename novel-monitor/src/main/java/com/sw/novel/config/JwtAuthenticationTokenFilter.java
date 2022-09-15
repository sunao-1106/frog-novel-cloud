package com.sw.novel.config;

import com.sw.common.constant.HomeBookTypeConstant;
import com.sw.common.utils.JwtUtil;

import com.sw.novel.entity.comm.CommUserMin;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Data
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisCache redisCache;

    @Autowired
    CommUserMin commUserMin;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //token 的值
        //获取token
        Cookie[] cookies = request.getCookies();
        //如果有值 拿到token
        String TOKEN_VALUE = "";

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //       System.out.println(cookie.getName()+"==="+cookie.getValue());
                if (HomeBookTypeConstant.COOKE_TOKEN.equals(cookie.getName())) {
                    //获取token中信息
                    TOKEN_VALUE = cookie.getValue();
                    //解析token

                    try {
                        //通过解析token得到的用户id
                        Claims claims = JwtUtil.parseJWT(TOKEN_VALUE);
                        commUserMin.setUserId(claims.getSubject());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("token非法");
                    }
                    //从redis中获取用户信息
                    String redisKey = "login:" + commUserMin.getUserId();
                    LoginUser loginUser = redisCache.getCacheObject(redisKey);
                    if (Objects.isNull(loginUser)) {
                        throw new RuntimeException("用户未登录");
                    }
                    //  存入SecurityContextHolder
                    //  获取权限信息封装到Authentication中
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(loginUser, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            //放行
            filterChain.doFilter(request, response);
            return;
        }

    }
}
