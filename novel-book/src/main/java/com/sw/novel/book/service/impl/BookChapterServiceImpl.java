package com.sw.novel.book.service.impl;

import com.sw.common.to.BookChapterTo;
import com.sw.novel.book.dao.BookContentMapper;
import com.sw.novel.book.entity.BookChapterEntity;
import com.sw.novel.book.dao.BookChapterMapper;
import com.sw.novel.book.entity.BookContentEntity;
import com.sw.novel.book.service.BookChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Service
public class BookChapterServiceImpl extends ServiceImpl<BookChapterMapper, BookChapterEntity> implements BookChapterService {

    @Autowired
    private BookContentMapper bookContentMapper;

    @Override
    public void saveChapter(BookChapterTo chapterTo) {
        // TODO 判断当前章节是否在表中已存在
        BookChapterEntity bookChapterEntity = new BookChapterEntity();
        BeanUtils.copyProperties(chapterTo, bookChapterEntity);
        // 封装其它参数
        bookChapterEntity.setCreateTime(new Date());
        bookChapterEntity.setUpdateTime(new Date());
        // 添加到数据库
        this.save(bookChapterEntity);

        BookContentEntity bookContentEntity = new BookContentEntity();
        BeanUtils.copyProperties(chapterTo.getBookContentTo(), bookContentEntity);
        // 封装其它参数
        bookContentEntity.setChapterId(bookChapterEntity.getId());
        // 添加到数据库
        bookContentMapper.insert(bookContentEntity);
    }
}
