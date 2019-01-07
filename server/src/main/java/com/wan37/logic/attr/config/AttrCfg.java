package com.wan37.logic.attr.config;

/**
 * 属性配置表
 *
 * @author linda
 */
public interface AttrCfg {

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
     * 描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 基础值，用于计算总面板伤害
     *
     * @return double
     */
    double getBaseAttackValue();

    /**
     * 基础值，用于计算面板总防御力
     *
     * @return double
     */
    double getBaseDefenseValue();

    /**
     * 装备基础评分，用于计算装备分数
     *
     * @return int
     */
    int getEquipBaseScore();
}
