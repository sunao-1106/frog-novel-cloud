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
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
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

    private final String bookInfoBaseUrl = "/book/info";

    private final String bookChapterBaseUrl = "/book/chapter";

    private final String homeBookBaseUrl = "/home/book";

    // TODO 这里可以使用配置文件的方式优化这些路径的配置
    // 不用登录就可以访问的url
    private final String[] commonPath = new String[] {
            bookInfoBaseUrl + "/get/*",
            bookInfoBaseUrl + "/recent",
            bookInfoBaseUrl + "/detail/*",
            homeBookBaseUrl + "/**"
    };

    // 已登录状态的普通用户
    private final String[] normalUserPath = new String[] {
            bookChapterBaseUrl + "/free/content/*"
    };

    // 已登录状态的会员用户
    private final String[] vipUserPath = new String[] {
            "/book/vip/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置权限路径
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

        for (String url : commonPath) { // 不需要登录即可访问
            // 会员用户也可以访问所有普通用户能访问的
            urlRegistry.antMatchers(url).hasRole("ANONYMOUS").antMatchers();
        }

        for (String url : normalUserPath) { // 普通用户
            // 会员用户也可以访问所有普通用户能访问的
            urlRegistry.antMatchers(url).hasRole("user.normal"); // 会自动拼接成ROLE_user.normal
        }

        for (String url : vipUserPath) {    // 会员用户
            urlRegistry.antMatchers(url).hasRole("user.vip");
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
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
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

    /**
     * 未携带或者携带无效的token访问接口时，自定义的返回结果（未登录）
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) ->  {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            R error = R.error(BizCodeEnum.INVALID_TOKEN.getCode(), BizCodeEnum.INVALID_TOKEN.getMessage());
            // 转化为json格式返回
            response.getWriter().println(JSON.toJSONString(error));
            response.getWriter().flush();
        });
    }

    /**
     * 角色权限继承关系
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_admin > ROLE_user.vip > ROLE_user.normal > ROLE_ANONYMOUS";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

}
