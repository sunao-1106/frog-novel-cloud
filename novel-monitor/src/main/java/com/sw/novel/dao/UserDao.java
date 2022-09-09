package com.sw.novel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.novel.entity.User;
import org.springframework.stereotype.Component;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-06 16:09:39
 */
@Component
public interface UserDao extends BaseMapper<User> {

}

