package com.wan37.event;

/**
 * 事件监听接口
 *
 * @author linda
 */
public interface GeneralEventListener<T> {

    /**
     * 监听事件执行
     *
     * @param event 事件
     */
    void execute(T event);
}
