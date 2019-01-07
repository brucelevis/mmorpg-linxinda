package com.wan37.behavior;

/**
 * @author linda
 */
public interface Behavior<T> {

    /**
     * 定义执行抽象方法
     *
     * @param context 上下文参数
     */
    void behave(T context);
}
