package com.wan37.logic.dungeon.config;

import java.util.List;

/**
 * 副本怪物组配置表
 */
public interface DungeonMonsterGroupCfg {

    Integer getGroupId();

    List<DungeonMonsterCfg> getMonsters();
}
