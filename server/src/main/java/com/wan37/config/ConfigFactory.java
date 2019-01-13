package com.wan37.config;

/**
 * 生成每条配置的工厂接口
 *
 * @author linda
 */
public interface ConfigFactory<T> {

    /**
     * 生成配置表方法
     *
     * @return T
     */
    T create();
}
