package com.sw.novel.user.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sw.novel.monitor.config.SecurityConfig;
import com.sw.novel.monitor.entity.LoginUser;
import com.sw.novel.monitor.entity.RoleDto;
import com.sw.novel.monitor.entity.UserDto;
import com.sw.novel.user.dao.RoleMapper;
import com.sw.novel.user.dao.UserMapper;
import com.sw.novel.user.entity.RoleEntity;
import com.sw.novel.user.entity.UserEntity;
import com.sw.novel.user.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author sunao
 * @since 2022/9/15
 * description: 用户模块权限认证配置类
 */
@Configuration
public class UserSecurityConfig extends SecurityConfig {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 重写loadUserByUsername方法
     * @return UserDetails对象
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 根据username查询用户信息
            UserEntity userEntity = userMapper.selectOne(
                    new LambdaQueryWrapper<UserEntity>()
                            .eq(UserEntity::getUsername, username)
            );
            if (userEntity == null) {
                // 用户不存在
                throw new UserNotFoundException();
            }
            // 查询该用户的权限信息
            RoleEntity roleEntity = roleMapper.selectById(userEntity.getUserRole());
            // 封装LoginUser对象返回
            UserDto userDto = new UserDto();
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(userEntity, userDto);
            BeanUtils.copyProperties(roleEntity, roleDto);
            return new LoginUser(userDto, roleDto);
        };
    }

}
