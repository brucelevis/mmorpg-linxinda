package com.wan37.exception;

/**
 * 自定义异常类
 *
 * @author linda
 */
public class GeneralErrorException extends RuntimeException {

    public GeneralErrorException(String message) {
        super(message);
    }
}