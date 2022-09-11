package com.sw.novel.home.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<HomeBookVo> getRecommendBookList() {    // TODO 根据用户历史记录中保存的小说的分类来筛选出猜你喜欢小说列表
        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.RECOMMEND_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                // 查找缓存
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.RECOMMEND_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search recommend book from database...");
                    // 查询数据库
                    homeBookList = getHomeBookFromDB(HomeBookTypeConstant.RECOMMEND_BOOK);
                    // 将对象存放到缓存中
                    // List集合没有实现Serializable接口 所以这里要将它手动转为Json字符串存入
                    redisTemplate.opsForValue().set(HomeBookRedisKey.RECOMMEND_BOOK, JSON.toJSON(homeBookList), HomeBookRedisKey.TIMEOUT);
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

    @Override
    public List<HomeBookVo> getSelectedBookList() {
        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.SELECTED_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                // 查找缓存
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.SELECTED_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search selected book from database...");
                    // 查询数据库
                    homeBookList = getHomeBookFromDB(HomeBookTypeConstant.SELECT_FISHED_BOOK);
                    // 将对象存放到缓存中
                    redisTemplate.opsForValue().set(HomeBookRedisKey.SELECTED_BOOK, JSON.toJSON(homeBookList), HomeBookRedisKey.TIMEOUT);
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

    @Override
    public List<HomeBookVo> getHotInWeekBookList() {
        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.HOT_IN_WEEK_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                // 查找缓存
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.HOT_IN_WEEK_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search week-hot book from database...");
                    // 查询数据库
                    homeBookList = getHomeBookFromDB(HomeBookTypeConstant.HOT_IN_WEEK);
                    // 将对象存放到缓存中
                    redisTemplate.opsForValue().set(HomeBookRedisKey.HOT_IN_WEEK_BOOK, JSON.toJSON(homeBookList), HomeBookRedisKey.TIMEOUT);
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

    @Override
    public void saveHotBooksInWeek(List<Long> bookIdList) {
        // 清空当前表中本周热度小说
        this.remove(
                new LambdaQueryWrapper<HomeBookEntity>().eq(HomeBookEntity::getType, HomeBookTypeConstant.HOT_IN_WEEK)
        );
        // 封装信息
        List<HomeBookEntity> homeBookEntityList = bookIdList.stream().map(item -> {
            HomeBookEntity homeBookEntity = new HomeBookEntity();
            homeBookEntity.setBookId(item);
            homeBookEntity.setType(HomeBookTypeConstant.HOT_IN_WEEK);
            homeBookEntity.setCreateTime(new Date());
            homeBookEntity.setUpdateTime(new Date());
            return homeBookEntity;
        }).collect(Collectors.toList());
        // 将最新的数据更新到表中
        this.saveBatch(homeBookEntityList);
    }

    @Override
    public List<HomeBookVo> getHotInMonthBookList() {
        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.HOT_IN_MONTH_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                // 查找缓存
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.HOT_IN_MONTH_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search month-hot book from database...");
                    // 查询数据库
                    homeBookList = getHomeBookFromDB(HomeBookTypeConstant.HOT_IN_MONTH);
                    // 将对象存放到缓存中
                    redisTemplate.opsForValue().set(HomeBookRedisKey.HOT_IN_MONTH_BOOK, JSON.toJSON(homeBookList), HomeBookRedisKey.TIMEOUT);
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

    @Override
    public List<HomeBookVo> getRecentUpdateBookList() {
        List<HomeBookVo> homeBookList = null;

        // 查找缓存
        Object data = redisTemplate.opsForValue().get(HomeBookRedisKey.RECENT_UPDATE_BOOK);

        if (data == null) {
            // 确保并发情况下只能一个线程进行一次数据库的查询
            synchronized (this) {
                // 查找缓存
                Object doubleCheckData = redisTemplate.opsForValue().get(HomeBookRedisKey.RECENT_UPDATE_BOOK);
                // 双重检查
                if (doubleCheckData == null) {
                    log.info("start to search recent update book from database...");
                    // 查询数据库
                    homeBookList = getHomeBookFromDB(HomeBookTypeConstant.RECENT_UPDATE);
                    // 将对象存放到缓存中
                    redisTemplate.opsForValue().set(HomeBookRedisKey.RECENT_UPDATE_BOOK, JSON.toJSON(homeBookList), HomeBookRedisKey.TIMEOUT_RECENT_UPDATE);
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

    @Override
    public void updateRecentUpdateBookList(List<BookInfoTo> bookInfoToList) {
        // 删除原首页中最新更新小说列表
        this.remove(
                new LambdaQueryWrapper<HomeBookEntity>()
                .eq(HomeBookEntity::getType, HomeBookTypeConstant.RECENT_UPDATE)
        );
        // 更新最新的小说
        List<HomeBookEntity> homeBookEntityList = bookInfoToList.stream().map(item -> {
            HomeBookEntity homeBookEntity = new HomeBookEntity();
            homeBookEntity.setBookId(item.getId());
            homeBookEntity.setType(HomeBookTypeConstant.RECENT_UPDATE);
            homeBookEntity.setCreateTime(new Date());
            homeBookEntity.setUpdateTime(new Date());
            return homeBookEntity;
        }).collect(Collectors.toList());
        this.saveBatch(homeBookEntityList);
    }

    /**
     * 根据首页小说类型从数据库查找对应的小说信息
     * @param type 小说类型
     */
    private List<HomeBookVo> getHomeBookFromDB(Integer type) {
        List<HomeBookVo> homeBookList;
        // 查找首页执行类型的小说列表
        List<HomeBookEntity> homeBookEntityList = this.list(
                new LambdaQueryWrapper<HomeBookEntity>()
                        .eq(HomeBookEntity::getType, type)
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
        return homeBookList;
    }

}
