package com.sw.novel.home.service;

import com.sw.novel.home.entity.HomeBookEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.novel.home.vo.HomeBookVo;

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

}
