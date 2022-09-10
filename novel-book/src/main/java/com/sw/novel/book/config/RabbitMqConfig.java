package com.sw.novel.book.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunao
 * @since 2022/9/9
 * description: RabbitMq配置类 主要用来创建以及绑定交换机、队列等
 */
@Configuration
public class RabbitMqConfig {

    /**
     * MQ的序列化机制
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * book.add.view.exchange
     */
    @Bean
    public Exchange bookAddViewExchange() {
        return new DirectExchange("book.add.view.exchange");
    }

    /**
     * 延迟队列
     * book.add.view.delay.queue
     */
    @Bean
    public Queue bookAddViewDelayQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-dead-letter-exchange", "book.add.view.exchange");  // 绑定死信交换机
        map.put("x-dead-letter-routing-key", "book.add.view");    // 绑定死信交换机路由key
        map.put("x-message-ttl", 5000L); // ttl: 5s
        return new Queue("book.add.view.delay.queue", true, false, false, map);
    }

    /**
     * book.add.view.queue
     */
    @Bean
    public Queue bookAddViewQueue() {
        return new Queue("book.add.view.queue", true, false, false);
    }

    /**
     * 绑定交换机
     */
    @Bean
    public Binding bookAddViewDelayQueueWithBookAddViewExchange() {
        return new Binding(
                "book.add.view.delay.queue",
                Binding.DestinationType.QUEUE,
                "book.add.view.exchange",
                "book.clicked",
                null
        );
    }

    /**
     * 绑定交换机
     */
    @Bean
    public Binding bookAddViewQueueWithBookAddViewExchange() {
        return new Binding(
                "book.add.view.queue",
                Binding.DestinationType.QUEUE,
                "book.add.view.exchange",
                "book.add.view",
                null
        );
    }

}
