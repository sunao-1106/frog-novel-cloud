package com.sw.novel.home.feign;

import com.sw.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sunao
 * @since 2022/9/8
 * description: 远程调用小说模块
 */
@FeignClient("novel-book")
public interface BookFeignService {

    /**
     * 根据id查询小说详细信息
     */
    @GetMapping("/book/info/get/{id}")
    R getBookInfoById(@PathVariable("id") Long id);

    /**
     * 查询最新更新章节小说
     */
    @GetMapping("/book/info/recent")
    R getRecentUpdateBook();
}
