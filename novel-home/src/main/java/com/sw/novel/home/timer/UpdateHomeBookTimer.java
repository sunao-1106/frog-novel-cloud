package com.sw.novel.home.timer;

import com.sw.common.constant.HomeBookRedisKey;
import com.sw.novel.home.service.HomeBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author sunao
 * @since 2022/9/9
 * description: 定时任务
 */
//@Component
@Slf4j
@RestController
public class UpdateHomeBookTimer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HomeBookService homeBookService;

    /**
     * 更新每周榜单
     */
    // TODO 修改定时器 每周执行一次
    @GetMapping("/update/week")
    public void updateHotInWeek() {
        HashOperations opsForHash = redisTemplate.opsForHash();
        // 查找缓存中小说的每周热度量信息
        Map<String, String> hotBooks = opsForHash.entries(HomeBookRedisKey.HOT_IN_WEEK_TOP);
        if (hotBooks != null) {
            ArrayList<Map.Entry<String, String>> entries = new ArrayList<>(hotBooks.entrySet());
            // 降序排序
            Collections.sort(entries, (k1, k2) -> {
                return k2.getValue().compareTo(k1.getValue());
            });

            List<Long> bookIdList = new ArrayList<>();

            // 收集排名前10的小说id
            for (Map.Entry<String, String> entry : entries) {
                if (bookIdList.size() >= 10) {
                    break;
                }
                bookIdList.add(Long.parseLong(entry.getKey()));
            }
            log.info("本周热度榜小说:{}", bookIdList);
            // 写入数据库
            homeBookService.saveHotBooksInWeek(bookIdList);
            // 清空缓存中的记录
            redisTemplate.delete(HomeBookRedisKey.HOT_IN_WEEK_TOP);
        }
    }

}
