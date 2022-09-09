package com.sw.novel.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author sunao
 * @since 2022/9/9
 * description: 前后端跨域问题
 */
@Configuration
public class FrogNovelCorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 放行全部原始头信息
        corsConfiguration.addAllowedHeader("*");
        // 允许所有请求方法跨域调用
        corsConfiguration.addAllowedMethod("*");
        // 设置你要允许的网站域名
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        // 允许跨域发送cookie
        corsConfiguration.setAllowCredentials(true);
        // 作用于所有路径
        source.registerCorsConfiguration("/**", corsConfiguration);

        return  new CorsWebFilter(source);
    }
}
