package com.sw.novel.config;

import com.alibaba.nacos.common.utils.HttpMethod;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.R;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/8 21:56
 */
public class SecurityConfigUser extends SecurityConfig {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf保护
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/user/*", "/user/test").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().and().httpBasic();

    }

    //权限不足时执行
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            System.out.println("======================================"+request);
        });

    }
}
