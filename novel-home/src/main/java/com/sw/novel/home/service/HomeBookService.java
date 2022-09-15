package com.sw.novel.home.service;

import com.sw.novel.home.entity.HomeBookEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.novel.home.vo.HomeBookVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
public interface HomeBookService extends IService<HomeBookEntity> {

    /**
     * 获取首页猜你喜欢小说列表
     */
    List<HomeBookVo> getRecommendBookList();

    /**
     * 获取首页精选完本小说列表
     */
    List<HomeBookVo> getSelectedBookList();

    /**
     * 获取首页本周热度榜小说列表
     */
    List<HomeBookVo> getHotInWeekBookList();

    /**
     * 更新本周热度榜小说
     */
    @Transactional
    void saveHotBooksInWeek(List<Long> bookIdList);
}
