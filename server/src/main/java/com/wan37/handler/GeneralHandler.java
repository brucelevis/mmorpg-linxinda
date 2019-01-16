package com.wan37.handler;

/**
 * 请求处理接口
 *
 * @author linda
 */
public interface GeneralHandler {

    /**
     * 处理请求消息抽象方法
     *
     * @param msg 请求消息
     */
    void handle(GeneralReqMsg msg);
}
