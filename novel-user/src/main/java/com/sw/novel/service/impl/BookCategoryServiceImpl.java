package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.BookCategoryDao;
import com.sw.novel.entity.BookCategory;
import com.sw.novel.service.BookCategoryService;
import org.springframework.stereotype.Service;

/**
 * (BookCategory)表服务实现类
 *
 * @author makejava
 * @since 2022-09-13 19:04:21
 */
@Service("bookCategoryService")
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryDao, BookCategory> implements BookCategoryService {

}

