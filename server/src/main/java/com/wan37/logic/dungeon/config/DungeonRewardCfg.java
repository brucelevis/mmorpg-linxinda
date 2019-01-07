package com.wan37.logic.dungeon.config;

/**
 * 副本奖励配置表
 */
public interface DungeonRewardCfg {

    Integer getId();

    int getAmount();

    /**
     * 概率
     */
    double getProbability();
}
