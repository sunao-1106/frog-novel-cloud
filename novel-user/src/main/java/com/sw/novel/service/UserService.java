package com.sw.novel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.common.utils.R;
import com.sw.novel.entity.User;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
public interface UserService extends IService<User> {

    R login(User user);
}

