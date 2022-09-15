package com.sw.novel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.novel.entity.UserPayRecord;
import org.springframework.stereotype.Component;

/**
 * (UserPayRecord)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-13 18:51:20
 */
@Component
public interface UserPayRecordDao extends BaseMapper<UserPayRecord> {

}

