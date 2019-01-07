package com.wan37.logic.dungeon.config;

/**
 * 副本奖励配置表
 *
 * @author linda
 */
public interface DungeonRewardCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

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
