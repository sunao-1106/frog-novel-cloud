package com.sw.novel.monitor.filter;

import com.alibaba.fastjson.JSON;
import com.sw.common.constant.HomeBookTypeConstant;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.JwtUtil;

import com.sw.common.utils.R;
import com.sw.novel.monitor.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO 过滤器会拦截静态资源 比如访问swagger接口的时候 不携带token可以访问 携带错误或过期的token不可以访问
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
                    Claims claims = null;
                    try {
                        //通过解析token得到的用户id
                        claims = JwtUtil.parseJWT(TOKEN_VALUE);
                    } catch (Exception e) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
                        if (e instanceof ExpiredJwtException) { // token已过期
                            R error = R.error(BizCodeEnum.LOGIN_EXPIRED.getCode(), BizCodeEnum.LOGIN_EXPIRED.getMessage());
                            // 转换为JSON格式返回
                            response.getWriter().println(JSON.toJSON(error));
                        } else if(e instanceof SignatureException) {    // 非法访问
                            R error = R.error(BizCodeEnum.ILLEGAL_ACCESS.getCode(), BizCodeEnum.ILLEGAL_ACCESS.getMessage());
                            // 转换为JSON格式返回
                            response.getWriter().println(JSON.toJSON(error));
                        } else {
                            R error = R.error(BizCodeEnum.UNDEFINED_ERROR.getCode(), BizCodeEnum.UNDEFINED_ERROR.getMessage());
                            // 转换为JSON格式返回
                            response.getWriter().println(JSON.toJSON(error));
                        }
                        return;
                    }
                    //从redis中获取用户信息
                    String redisKey = "login:" + claims.getSubject();
                    Object o = redisTemplate.opsForValue().get(redisKey);
                    LoginUser loginUser = JSON.parseObject(JSON.toJSONString(o), LoginUser.class);
                    if (Objects.isNull(loginUser)) {
                        throw new RuntimeException("用户未登录");
                    }
                    //  存入SecurityContextHolder
                    //  获取权限信息封装到Authentication中
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        //放行
        filterChain.doFilter(request, response);
    }
}
