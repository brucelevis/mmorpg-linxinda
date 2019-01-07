package com.wan37.logic.dungeon.config;

import com.wan37.logic.scene.config.SceneCfg;

import java.util.List;
import java.util.Map;

/**
 * 副本配置表
 *
 * @author linda
 */
public interface DungeonCfg {

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
     * 副本限时（分钟）
     *
     * @return int
     */
    int getLimitTime();

    /**
     * 场景id
     *
     * @return Integer
     * @see SceneCfg#getId()
     */
    Integer getSceneId();

    /**
     * 描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 副本奖励
     *
     * @return List<DungeonRewardCfg>
     */
    List<DungeonRewardCfg> getReward();

    /**
     * 副本怪物组配置
     *
     * @return Map
     */
    Map<Integer, DungeonMonsterGroupCfg> getMonsters();

    /**
     * 通关描述（发送世界）
     *
     * @return String
     */
    String getCompleteTip();

    /**
     * 人数要求
     *
     * @return int
     */
    int getLimitNum();
}
