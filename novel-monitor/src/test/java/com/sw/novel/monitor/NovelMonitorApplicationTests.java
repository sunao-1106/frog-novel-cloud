package com.sw.novel.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class NovelMonitorApplicationTests {

    /**
     * 测试 spring security 的 BCryptPasswordEncoder 加密
     */
    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println("====加密后===="+encode);

        /**
         * 判断加密前后是否一致
         */
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$7ahDXdqKM4jyK.UOHOmX5eMiTzWvCVRUwHbJ59FkPS5cG/ReaygA.");
        System.out.println("=====是否一致====="+matches);

    }

}
