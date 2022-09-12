package com.sw.common.utils;

import com.sw.common.exception.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/11 13:40
 * 自定义异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException{

    private int code  = 500;
    private String msg = "服务器异常";

    public AppException(BizCodeEnum bizCodeEnum) {
        super();
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

}
