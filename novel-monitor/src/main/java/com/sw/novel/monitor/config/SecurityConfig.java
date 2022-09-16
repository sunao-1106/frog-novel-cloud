package com.sw.novel.monitor.config;

import com.alibaba.fastjson.JSON;
import com.sw.common.enums.RoleEnum;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.R;
import com.sw.novel.monitor.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/6 22:00
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 是否不去设置就不需要权限？
    // 那么就可以将需求登录之后才能访问的路径设置普通用户权限(不满足则提示登录)
    // 不需要登录状态就可以访问的路径 不设置权限
    private final String[] normalUserPath = new String[] {
            "/book/info/**",
            "/book/chapter/**"
           // "/home/book/**"
    };

    private final String[] vipUserPath = new String[] {
            "/book/vip/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置权限路径
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

        // 设置对普通用户可以访问的url
        // TODO 这种方式会导致空指针异常 原因是每个模块的SecurityConfig配置不是公共的 normalUserPathConfig这些配置只在user模块中定义了 所以启动book模块执行到这里就会报错 空指针
//        if (normalUserPathConfig().getUrl() != null || vipUserPathConfig().getUrl().size() > 0) {
//            for (String url :  normalUserPathConfig().getUrl()) {
//                urlRegistry.antMatchers(url).hasAnyAuthority(RoleEnum.NORMAL_USER.getSign());
//            }
//        }

//        if (vipUserPathConfig().getUrl() != null || vipUserPathConfig().getUrl().size() > 0) {
//            // 设置会员用户可以访问的url
//            for (String url :  vipUserPathConfig().getUrl()) {
//                urlRegistry.antMatchers(url).hasAnyAuthority(RoleEnum.VIP_USER.getSign());
//            }
//        }
        for (String url : normalUserPath) {
            // 会员用户也可以访问所有普通用户能访问的
            urlRegistry.antMatchers(url).hasAnyAuthority(RoleEnum.NORMAL_USER.getSign(), RoleEnum.VIP_USER.getSign());
        }

        for (String url : vipUserPath) {
            urlRegistry.antMatchers(url).hasAnyAuthority(RoleEnum.VIP_USER.getSign());
        }

        http.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers( "/user/auth/**")// 对登录注册要允许匿名访问
                .permitAll()
//                .antMatchers("/**")
//                .permitAll()    // 测试放行所有
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        http.headers().cacheControl();

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 权限校验失败的处理方法
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
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

    /**
     * 过滤器
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public NormalUserPathConfig normalUserPathConfig() {
        return new NormalUserPathConfig();
    }

    @Bean
    public VipUserPathConfig vipUserPathConfig() {
        return new VipUserPathConfig();
    }

    /**
     * 当权限校验失败时，自定义的返回结果
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            R error = R.error(BizCodeEnum.LACK_OF_PERMISSION.getCode(), BizCodeEnum.LACK_OF_PERMISSION.getMessage());
            response.getWriter().println(JSON.toJSONString(error));
            response.getWriter().flush();
        });
    }

}
