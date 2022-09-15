package com.sw.novel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.novel.entity.BookCategory;
import org.springframework.stereotype.Component;

/**
 * (BookCategory)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-13 19:04:21
 */
@Component
public interface BookCategoryDao extends BaseMapper<BookCategory> {

}

