package com.sw.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author sunao
 * @since 2022/9/5
 * description: 异常响应状态码
 */
public enum BizCodeEnum {

    //    用户接口 错误 1000 - 1100
    USER_PASSWORD(1000,"用户名密码错误"),
    USER_POWER(1001,"用户权限不足"),
    USER_USERNAME(1002,"用户异常"),
    USER_FORMAT(1003,"上传文件格式错误"),
    USER_REGISTER("注册成功,请登录"),
    USER_REGISTER_ERROR(1005,"注册失败,请重新注册"),
    ILLEGAL_ACCESS(1006, "非法访问"),
    LOGIN_EXPIRED(1007, "登录过期，请重新登录"),
    UNDEFINED_ERROR(1008, "未知错误，请联系系统管理员"),
    LACK_OF_PERMISSION(1009, "访问权限不足");

    private int code;
    private String message;

    BizCodeEnum(int code ,String message) {
        this.code = code;
        this.message = message;
    }

    BizCodeEnum(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
