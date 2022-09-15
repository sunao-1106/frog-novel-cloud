package com.sw.novel.book.service;

import com.sw.common.to.BookInfoTo;
import com.sw.novel.book.entity.BookInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
public interface BookInfoService extends IService<BookInfoEntity> {

    /**
     * 添加小说
     * @return 添加成功后的bookId
     */
    Long saveBookInfo(BookInfoTo bookInfoTo);

    /**
     * 根据小说id获取小说信息
     */
    BookInfoTo getBookById(Long id);

    /**
     * 增加小说浏览量
     * @param viewCountOnceAdd 每点击一次增加的浏览量
     */
    void addViewCountByBookId(Long bookId, Integer viewCountOnceAdd);
}
