package com.sw.novel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

public class SecurityConfigUser extends SecurityConfig {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf保护
        http.csrf().disable();

        http.authorizeRequests()
                //.antMatchers("/user/*", "/user/test").permitAll()
                //.anyRequest().authenticated()
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll()
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