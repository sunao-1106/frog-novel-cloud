package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.BookChapterDao;
import com.sw.novel.entity.BookChapter;
import com.sw.novel.service.BookChapterService;
import org.springframework.stereotype.Service;

/**
 * (BookChapter)表服务实现类
 *
 * @author makejava
 * @since 2022-09-15 10:18:32
 */
@Service("bookChapterService")
public class BookChapterServiceImpl extends ServiceImpl<BookChapterDao, BookChapter> implements BookChapterService {

}

