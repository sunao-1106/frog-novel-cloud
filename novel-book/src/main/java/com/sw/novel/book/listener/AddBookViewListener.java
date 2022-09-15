package com.sw.novel.book.listener;

import com.rabbitmq.client.Channel;
import com.sw.common.constant.ClickBookConstant;
import com.sw.common.constant.HomeBookRedisKey;
import com.sw.novel.book.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author sunao
 * @since 2022/9/9
 * description: 监听队列
 */
@Slf4j
@Component
public class AddBookViewListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BookInfoService bookInfoService;

    @RabbitListener(queues = "book.add.view.queue")
    @RabbitHandler
    public void addBookViewCount(Long bookId, Message message, Channel channel) {
        // TODO 加锁
        log.info("click book:{}", bookId);
        // 获取redis中存储该小说的点击次数
        Integer clickedTime = (Integer) redisTemplate.opsForValue().get(ClickBookConstant.BOOK_CLICK_TIME + bookId);
        if (clickedTime == null) {
            clickedTime = 0;
        }
        // +1 重新保存到缓存
        redisTemplate.opsForValue().set(ClickBookConstant.BOOK_CLICK_TIME + bookId, ++clickedTime);

        // 每本小说的点击次数达到10 进行一次数据库的写操作
        if (clickedTime >= 10) {
            // 写入数据库 增加该小说的浏览量viewCount
            log.info("start to add book view count to database, bookId:{}", bookId);
            bookInfoService.addViewCountByBookId(bookId, ClickBookConstant.VIEW_COUNT_ONCE_ADD * clickedTime);
            // 删除该小说点击次数在缓存中的记录
            redisTemplate.delete(ClickBookConstant.BOOK_CLICK_TIME + bookId);

            HashOperations opsHash = redisTemplate.opsForHash();
            // 在缓存中记录该小说的本周热度量
            opsHash.increment(HomeBookRedisKey.HOT_IN_WEEK_TOP, bookId.toString(), (long) ClickBookConstant.VIEW_COUNT_ONCE_ADD * clickedTime);
            // 在缓存中记录该小说的本月热度量
            opsHash.increment(HomeBookRedisKey.HOT_IN_MONTH_TOP, bookId.toString(), (long) ClickBookConstant.VIEW_COUNT_ONCE_ADD * clickedTime);

        }
    }

}
