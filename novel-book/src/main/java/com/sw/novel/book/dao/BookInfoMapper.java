package com.sw.novel.book.dao;

import com.sw.novel.book.entity.BookInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
public interface BookInfoMapper extends BaseMapper<BookInfoEntity> {

    /**
     * 增加小说浏览量
     * @param viewCountOnceAdd 每点击一次增加的浏览量
     */
    void addViewCountByBookId(@Param("id") Long bookId, @Param("count") Integer viewCountOnceAdd);
}
