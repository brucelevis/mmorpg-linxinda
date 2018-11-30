package com.wan37.logic.dungeon.config;

import java.util.List;
import java.util.Map;

public interface DungeonCfg {

    Integer getId();

    String getName();

    int getLimitTime();

    Integer getSceneId();

    String getDesc();

    List<DungeonRewardCfg> getReward();

    Map<Integer, DungeonMonsterGroupCfg> getMonsters();

    String getCompleteTip();
}
