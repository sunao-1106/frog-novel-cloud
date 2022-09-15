package com.sw.novel.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.novel.dao.UserDao;
import com.sw.novel.entity.User;
import com.sw.novel.entity.vo.UserVo;
import com.sw.novel.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NovelUserApplicationTests {

    @Resource
    UserDao userDao;

    @Test
    void contextLoads() {

    }


}
