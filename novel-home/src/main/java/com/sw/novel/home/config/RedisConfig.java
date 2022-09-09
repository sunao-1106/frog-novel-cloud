package com.sw.novel.home.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author sunao
 * @since 2022/9/9
 * description: 缓存配置类
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义缓存序列化机制
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 自定义key序列化方式,直接将String字符串直接作为redis中的key
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 自定义value序列化方式，序列化成json格式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new 					Jackson2JsonRedisSerializer(Object.class);
        //jackson底层的序列化和反序列化使用的是ObjectMapper,我们可以通过ObjectMapper设置序列化信息
        // 设置值的默认类型，即类信息
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 添加进序列化中
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

}
