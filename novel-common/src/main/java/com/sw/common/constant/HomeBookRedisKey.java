package com.sw.common.constant;

import java.time.Duration;

/**
 * @author sunao
 * @since 2022/9/8
 * description: 首页小说保存在缓存中的key值
 */
public interface HomeBookRedisKey {

    /**
     * 猜你喜欢
     */
    String RECOMMEND_BOOK = "home_recommend_book_list";

    /**
     * 精选完本
     */
    String SELECTED_BOOK = "home_selected_book_list";

    /**
     * 本周热度榜小说
     */
    String HOT_IN_WEEK_BOOK = "home_hot_in_week_book_list";

    /**
     * 本周热度榜小说
     */
    String HOT_IN_MONTH_BOOK = "home_hot_in_month_book_list";

    /**
     * 首页小说缓存过期时间: 3天
     */
    Duration TIMEOUT = Duration.ofSeconds(60 * 60 * 24 * 3);

    /**
     * 存储各小说本周的热度量 用于统计
     */
    String HOT_IN_WEEK_TOP = "home_hot_in_week_top";

    /**
     * 存储各小说本月的热度量 用于统计
     */
    String HOT_IN_MONTH_TOP = "home_hot_in_month_top";

}
