package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.BookInfoDao;
import com.sw.novel.entity.BookInfo;
import com.sw.novel.service.BookInfoService;
import org.springframework.stereotype.Service;

/**
 * (BookInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-09-13 18:52:17
 */
@Service("bookInfoService")
public class BookInfoServiceImpl extends ServiceImpl<BookInfoDao, BookInfo> implements BookInfoService {

}

