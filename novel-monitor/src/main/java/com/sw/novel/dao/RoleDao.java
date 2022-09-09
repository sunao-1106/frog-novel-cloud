package com.sw.novel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.novel.entity.Role;
import org.springframework.stereotype.Component;

/**
 * (Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-07 21:26:02
 */
@Component
public interface RoleDao extends BaseMapper<Role> {

}

