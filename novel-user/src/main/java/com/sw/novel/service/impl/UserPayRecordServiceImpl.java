package com.sw.novel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.dao.UserPayRecordDao;
import com.sw.novel.entity.UserPayRecord;
import com.sw.novel.service.UserPayRecordService;
import org.springframework.stereotype.Service;

/**
 * (UserPayRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-09-13 18:51:20
 */
@Service("userPayRecordService")
public class UserPayRecordServiceImpl extends ServiceImpl<UserPayRecordDao, UserPayRecord> implements UserPayRecordService {

}

