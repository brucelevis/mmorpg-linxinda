package com.wan37.logic.summoning.config;

import com.wan37.logic.attr.config.AttrCfg;

/**
 * 召唤兽初始化属性配置表
 *
 * @author linda
 */
public interface SummoningInitAttrCfg {

    /**
     * 属性id
     *
     * @return Integer
     * @see AttrCfg#getId()
     */
    Integer getId();

    /**
     * 值
     *
     * @return double
     */
    double getValue();
}
