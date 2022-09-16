package com.sw.novel.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author sunao
 * @since 2022/9/16
 * description: 普通用户可以访问的url
 */
@ConfigurationProperties("role.path.normal-user")
public class NormalUserPathConfig {

    private List<String> url;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
