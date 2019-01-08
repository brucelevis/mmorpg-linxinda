package com.wan37.logic.monster.config;

import com.wan37.logic.attr.config.AttrCfg;

/**
 * 怪物初始属性配置
 *
 * @author linda
 */
public interface MonsterInitAttrCfg {

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
