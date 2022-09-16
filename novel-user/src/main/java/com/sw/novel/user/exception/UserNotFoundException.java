package com.sw.novel.user.exception;

/**
 * @author sunao
 * @since 2022/9/16
 * description: 用户不存在异常类
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("用户不存在");
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
