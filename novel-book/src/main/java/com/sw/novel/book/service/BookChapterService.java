package com.sw.novel.book.service;

import com.sw.common.to.BookChapterTo;
import com.sw.novel.book.entity.BookChapterEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.novel.book.vo.BookChapterContentVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
public interface BookChapterService extends IService<BookChapterEntity> {

    /**
     * 保存小说章节信息
     */
    @Transactional
    void saveChapter(BookChapterTo chapterTo);

    /**
     * 查询对应的小说章节的内容
     * 免费章节
     */
    BookChapterContentVo getFreeChapterContent(Long id) throws IllegalAccessException;
}
