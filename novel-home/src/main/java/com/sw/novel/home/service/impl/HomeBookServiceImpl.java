package com.sw.novel.home.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sw.common.constant.HomeBookRedisKey;
import com.sw.common.constant.HomeBookTypeConstant;
import com.sw.common.to.BookInfoTo;
import com.sw.common.utils.R;
import com.sw.novel.home.entity.HomeBookEntity;
import com.sw.novel.home.dao.HomeBookMapper;
import com.sw.novel.home.feign.BookFeignService;
import com.sw.novel.home.service.HomeBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.home.vo.HomeBookVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Slf4j
@Service
public class HomeBookServiceImpl extends ServiceImpl<HomeBookMapper, HomeBookEntity> implements HomeBookService {

    @Autowired
    private BookFeignService bookFeignService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<HomeBookVo> getRecommendBookList() {

        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.RECOMMEND_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.RECOMMEND_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search recommend book from database...");
                    // 查找首页猜你喜欢小说列表
                    List<HomeBookEntity> homeBookEntityList = this.list(
                            new LambdaQueryWrapper<HomeBookEntity>()
                                    .eq(HomeBookEntity::getType, HomeBookTypeConstant.RECOMMEND_BOOK)
                    );

                    homeBookList = homeBookEntityList.stream().map(item -> {
                        HomeBookVo homeBookVo = null;
                        // 查询这些小说的详细信息
                        R r = bookFeignService.getBookInfoById(item.getBookId());
                        if (r.get("data") != null) {
                            homeBookVo = new HomeBookVo();
                            BookInfoTo bookInfoTo = r.getData(BookInfoTo.class);
                            BeanUtils.copyProperties(bookInfoTo, homeBookVo);
                        }
                        return homeBookVo;
                    }).collect(Collectors.toList());

                    // 将对象存放到缓存中
                    // List集合没有实现Serializable接口 所以这里要将它手动转为Json字符串存入
                    redisTemplate.opsForValue().set(HomeBookRedisKey.RECOMMEND_BOOK, JSON.toJSON(homeBookList));
                    return homeBookList;
                } else {    // 双重检查发现缓存中存在
                    // 将缓存中的Json字符串转为原集合
                    homeBookList = JSON.parseArray(JSON.toJSONString(doubleCheckData), HomeBookVo.class);
                }
                return homeBookList;
            }
        }

        // 将缓存中的Json字符串转为原集合
        homeBookList = JSON.parseArray(JSON.toJSONString(data), HomeBookVo.class);
        return homeBookList;

    }
}
