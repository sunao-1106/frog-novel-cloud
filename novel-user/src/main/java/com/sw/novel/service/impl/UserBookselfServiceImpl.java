package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.UserBookselfDao;
import com.sw.novel.entity.UserBookself;
import com.sw.novel.service.UserBookselfService;
import org.springframework.stereotype.Service;

/**
 * (UserBookself)表服务实现类
 *
 * @author makejava
 * @since 2022-09-13 18:51:58
 */
@Service("userBookselfService")
public class UserBookselfServiceImpl extends ServiceImpl<UserBookselfDao, UserBookself> implements UserBookselfService {

}

