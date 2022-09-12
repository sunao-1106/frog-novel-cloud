package com.sw.novel.utils;

import com.sw.common.utils.AppException;
import com.sw.common.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/11 13:31
 * 统一 异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 捕获自定义的返回类型
     * @return
     */
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public R exceptionHandler(AppException appException){
            //转换为自己的异常类型
            //统一返回处理结果
            return R.error(appException.getCode(),appException.getMsg());

    }
    //如果不是我们自定义异常类
//    @ExceptionHandler(value = {Exception.class})
//    @ResponseBody
//    public R exceptionHandler(Exception e){
//        //转换为自己的异常类型
//        //统一返回处理结果
//        System.out.println(e);
//        return R.error(6666,"后端未知错误");
//
//    }


}
