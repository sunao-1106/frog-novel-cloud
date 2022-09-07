package com.sw.novel.crawler.source.factroy;

import com.sw.novel.crawler.domain.CrawlerSource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * @author sunao
 * @since 2022/9/7
 * description: 爬虫源工厂类
 */
public class CrawlerSourceFactory {
    public static HashMap<String, CrawlerSource> map = new HashMap<>();

    // 加载配置文件，只需要加载一次，所以用静态代码块来完成
    static {
        Properties properties = new Properties();
        InputStream is = CrawlerSourceFactory.class.getClassLoader().getResourceAsStream("source.properties");
        try {
            properties.load(is);
            // 获取配置文件中的键名
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                // 获取对应key的value：全类名
                String className = properties.getProperty((String) key);
                // 通过反射来创建该类实例
                CrawlerSource source = (CrawlerSource) Class.forName(className).newInstance();
                map.put((String) key, source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CrawlerSource getCrawlerSource(String sourceName) {
        return map.get(sourceName);
    }
}
