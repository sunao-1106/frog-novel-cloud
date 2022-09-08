package com.sw.novel.book.service.impl;

import com.sw.common.to.BookInfoTo;
import com.sw.novel.book.entity.BookInfoEntity;
import com.sw.novel.book.dao.BookInfoMapper;
import com.sw.novel.book.service.BookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfoEntity> implements BookInfoService {

    @Override
    public Long saveBookInfo(BookInfoTo bookInfoTo) {
        // TODO 判断当前小说是否在表中已存在
        BookInfoEntity bookInfoEntity = new BookInfoEntity();
        BeanUtils.copyProperties(bookInfoTo, bookInfoEntity);
        // 封装其它参数信息
        bookInfoEntity.setCreatTime(new Date());
        bookInfoEntity.setUpdateTime(new Date());
        this.save(bookInfoEntity);
        return bookInfoEntity.getId();
    }

    @Override
    public BookInfoTo getBookById(Long id) {
        BookInfoTo bookInfoTo = new BookInfoTo();
        BeanUtils.copyProperties(this.getById(id), bookInfoTo);
        return bookInfoTo;
    }

}
