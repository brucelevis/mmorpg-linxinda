package com.wan37.logic.monster.config;

/**
 * 怪物掉物配置表
 *
 * @author linda
 */
public interface MonsterItemCfg {

    /**
     * 物品（实物，虚物）id
     *
     * @return Integer
     */
    Integer getCfgId();

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();

    /**
     * 概率
     *
     * @return double
     */
    double getProbability();
}
