package com.sw.novel.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.common.utils.R;
import com.sw.novel.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
public interface UserService extends IService<UserEntity> {


    String login(UserEntity user);

    R outLogin();

    R Register(UserEntity user);

    R UserInfo();

    R bookHistory();
}

