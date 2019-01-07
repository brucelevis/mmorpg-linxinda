package com.wan37.logic.dungeon.config;

import com.wan37.logic.scene.config.SceneCfg;

import java.util.List;
import java.util.Map;

/**
 * 副本配置表
 */
public interface DungeonCfg {

    Integer getId();

    String getName();

    /**
     * 副本限时（分钟）
     */
    int getLimitTime();

    /**
     * @see SceneCfg#getId()
     */
    Integer getSceneId();

    String getDesc();

    /**
     * 副本奖励
     */
    List<DungeonRewardCfg> getReward();

    /**
     * 副本怪物组配置
     */
    Map<Integer, DungeonMonsterGroupCfg> getMonsters();

    /**
     * 通关描述（发送世界）
     */
    String getCompleteTip();

    /**
     * 人数要求
     */
    int getLimitNum();
}
