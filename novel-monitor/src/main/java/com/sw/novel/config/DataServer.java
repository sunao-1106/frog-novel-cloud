package com.sw.novel.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.AppException;
import com.sw.novel.dao.RoleDao;
import com.sw.novel.dao.UserDaoMon;
import com.sw.novel.entity.Role;
import com.sw.novel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DataServer implements UserDetailsService {

    @Autowired
    private UserDaoMon userDaoMon;

    @Autowired
    private RoleDao roleDao;

    /**
     * 吧校验的数据替换为数据库的数据
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username 查询用户的信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);

        User user = userDaoMon.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            //spring security总会帮我们捕获在其中的 全部异常
            throw new AppException(BizCodeEnum.USER_PASSWORD);
        }

        Role role = roleDao.selectById(user.getUserRole());
        List<String> list = new ArrayList<>(Arrays.asList(role.getRoleSign()));
        return new LoginUser(user,list);
    }
}

