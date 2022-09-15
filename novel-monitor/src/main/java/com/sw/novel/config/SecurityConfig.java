package com.sw.novel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/6 22:00
 */
@Configuration
@ComponentScan("com.sw.novel")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  设置表单
//        http.formLogin()
//                //和前端的表单属性对应的名字
//                .usernameParameter("username")
//                //和前端的表单属性对应的密码
//                .passwordParameter("password")
//                //当发现/login 时认为是登录，必须和表单提交的地址一样，去执行 UserDetailsServiceImpl
//                .loginProcessingUrl("/login")
//                //更改默认的登录页面
//                .loginPage("/login");

                //登录成功后的跳转页面
                //.successForwardUrl("/toMain")

        //禁用csrf保护
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/user/*").permitAll()
                //上传接口
                .antMatchers("/*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().and().httpBasic();

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 设置登录密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //权限不足时执行
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            System.out.println("======================================"+request);
      });

    }
}
