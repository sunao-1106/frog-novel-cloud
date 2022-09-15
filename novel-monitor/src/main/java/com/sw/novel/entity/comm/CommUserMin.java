package com.sw.novel.entity.comm;

import io.lettuce.core.dynamic.annotation.Command;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/14 9:14
 */
@Data
@Component
public class CommUserMin {

    private String userId;

}
