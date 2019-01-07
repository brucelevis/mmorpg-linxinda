package com.wan37.logic.mission.config;

/**
 * 任务奖励物品配置表
 *
 * @author linda
 */
public interface MissionRewardCfg {

    /**
     * 物品（实物，虚物）id
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
}
