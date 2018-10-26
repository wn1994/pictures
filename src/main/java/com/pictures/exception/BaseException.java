package com.pictures.exception;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
