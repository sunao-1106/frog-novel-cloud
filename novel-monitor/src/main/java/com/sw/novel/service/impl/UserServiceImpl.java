package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.UserDaoMon;
import com.sw.novel.entity.User;
import com.sw.novel.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDaoMon, User> implements UserService {

}

