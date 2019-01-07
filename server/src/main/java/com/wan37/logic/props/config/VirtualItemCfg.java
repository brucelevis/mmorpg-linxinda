package com.wan37.logic.props.config;

/**
 * 虚物配置表
 *
 * @author linda
 */
public interface VirtualItemCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 名字
     *
     * @return String
     */
    String getName();

    /**
     * 最大上限
     *
     * @return long
     */
    long getMaxOverlay();
}
