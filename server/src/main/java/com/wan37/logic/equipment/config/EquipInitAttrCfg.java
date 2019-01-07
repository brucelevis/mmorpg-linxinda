package com.wan37.logic.equipment.config;

import com.wan37.logic.attr.config.AttrCfg;

/**
 * 装备初始属性配置表
 *
 * @author linda
 */
public interface EquipInitAttrCfg {

    /**
     * 属性id
     *
     * @return Integer
     * @see AttrCfg#getId()
     */
    Integer getAttrCfgId();

    /**
     * 基础值
     *
     * @return double
     */
    double getBaseValue();

    /**
     * 步长
     *
     * @return double
     */
    double getStep();

    /**
     * 随机最大步长倍数
     *
     * @return int
     */
    int getMaxRandInt();
}
