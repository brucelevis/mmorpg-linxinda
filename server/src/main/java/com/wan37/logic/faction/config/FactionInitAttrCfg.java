package com.wan37.logic.faction.config;

import com.wan37.logic.attr.config.AttrCfg;

/**
 * 门派初始属性配置表
 *
 * @author linda
 */
public interface FactionInitAttrCfg {

    /**
     * 属性id
     *
     * @return Integer
     * @see AttrCfg#getId()
     */
    Integer getAttrCfgId();

    /**
     * 值
     *
     * @return double
     */
    double getValue();
}
