package com.sw.novel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.common.utils.R;
import com.sw.novel.entity.User;
import com.sw.novel.entity.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
public interface UserService extends IService<User> {


    R login(User user);

    R outLogin();

    R Register(User user);

    R UserInfo();

    R bookHistory();
}

