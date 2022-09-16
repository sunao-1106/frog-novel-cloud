package com.sw.novel.user.exception;

/**
 * @author sunao
 * @since 2022/9/16
 * description: 认证失败异常
 */
public class AuthenticateException extends RuntimeException {

    public AuthenticateException(String msg) {
        super(msg);
    }

}
