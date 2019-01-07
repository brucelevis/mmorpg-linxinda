package com.wan37.logic.attr.config;

/**
 * 属性配置表
 */
public interface AttrCfg {

    Integer getId();

    String getName();

    String getDesc();

    /**
     * 基础值，用于计算总面板伤害
     */
    double getBaseAttackValue();

    /**
     * 基础值，用于计算面板总防御力
     */
    double getBaseDefenseValue();

    /**
     * 装备基础评分，用于计算装备分数
     */
    int getEquipBaseScore();
}
