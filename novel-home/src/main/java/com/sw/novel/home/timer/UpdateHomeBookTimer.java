package com.sw.novel.home.timer;

import com.alibaba.fastjson.JSON;
import com.sw.common.constant.HomeBookRedisKey;
import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.home.entity.HomeBookEntity;
import com.sw.novel.home.feign.BookFeignService;
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

import java.awt.print.Book;
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

    @Autowired
    private BookFeignService bookFeignService;

    /**
     * 更新每周榜单
     */
    // TODO 修改为定时器 每周执行一次
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
            // 如果当前缓存中存在周排行榜小说列表数据 则删除掉
            // 确保用户下一次访问可以访问一下数据库 拿到最新的数据
            if (redisTemplate.opsForValue().get(HomeBookRedisKey.HOT_IN_WEEK_BOOK) != null) {
                redisTemplate.delete(HomeBookRedisKey.HOT_IN_WEEK_BOOK);
            }
        }
    }

    /**
     * 更新首页最新更新小说列表
     * TODO 修改为定时操作 每天更新一次
     */
    @GetMapping("/update/recent")
    public void updateRecentUpdate() {
        // 查询数据库中最新更新章节的小说：根据recent_chapter_update_time:降序
        // 使用分页 限制从头开始 只查询15个
        R r = bookFeignService.getRecentUpdateBook();
        List data = r.getData(List.class);
        List<BookInfoTo> bookInfoToList = JSON.parseArray(data.toString(), BookInfoTo.class);
        // 将最新的小说更新到home_book表中
        homeBookService.updateRecentUpdateBookList(bookInfoToList);
        // 删除当前缓存中的最新更新小说列表数据
        // 确保用户下一次访问可以访问一下数据库 拿到最新的数据
        if (redisTemplate.opsForValue().get(HomeBookRedisKey.RECENT_UPDATE_BOOK) != null) {
            redisTemplate.delete(HomeBookRedisKey.RECENT_UPDATE_BOOK);
        }
    }

}
