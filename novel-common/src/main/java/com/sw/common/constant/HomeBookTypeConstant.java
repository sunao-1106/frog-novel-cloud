package com.sw.common.constant;

import io.swagger.models.auth.In;

/**
 * @author sunao
 * @since 2022/9/8
 * description:
 */
public interface HomeBookTypeConstant {

    /**
     * 轮播图小说
     */
    Integer CAROUSEL_BOOK = 0;

    /**
     * 猜你喜欢
     */
    Integer RECOMMEND_BOOK = 1;

    /**
     * 精选完本
     */
    Integer SELECT_FISHED_BOOK = 2;

    /**
     * 本周点击榜
     */
    Integer HOT_IN_WEEK = 3;

    /**
     * 最新更新
     */
    Integer RECENT_UPDATE = 4;

    /**
     * 本月点击榜
     */
    Integer HOT_IN_MONTH = 5;

}
