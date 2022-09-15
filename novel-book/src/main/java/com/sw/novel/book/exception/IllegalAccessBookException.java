package com.sw.novel.book.exception;

/**
 * @author sunao
 * @since 2022/9/15
 * description: 非法访问异常对象
 */
public class IllegalAccessBookException extends RuntimeException {

    public IllegalAccessBookException() {
        super("非法访问");
    }

}
