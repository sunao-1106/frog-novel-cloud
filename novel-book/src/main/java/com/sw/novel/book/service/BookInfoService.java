package com.sw.novel.book.service;

import com.sw.common.to.BookInfoTo;
import com.sw.novel.book.entity.BookInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.novel.book.vo.BookDetailVo;

import java.util.List;

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

    /**
     * 查询最新更新章节小说
     */
    List<BookInfoTo> getRecentUpdateBookList();

    /**
     * 通过小说id获取其详细信息，包括小说目录章节、小说评论
     */
    BookDetailVo getBookDetailById(Long id);
}
