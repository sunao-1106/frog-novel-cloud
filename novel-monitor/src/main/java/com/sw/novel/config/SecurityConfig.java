package com.sw.novel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/6 22:00
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


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

        //授权认证
//        http.authorizeHttpRequests()
//                // login 无需认证
//                .antMatchers("login").permitAll();
//                //权限认证                 可以访问的页面             需要的权限（区分大小写 可以多个）
//              //  .antMatchers("/mian").hasAnyAuthority("admin","admin01")
//                //角色控制                                          需要角色
//              //  .antMatchers("main").hasAnyRole("abc");
//
//                //设置全部请求都要被认证
//                // .anyRequest().authenticated();
        //禁用csrf保护

    }

    /**
     * 设置登录密码加密
     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
