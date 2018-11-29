package com.wan37.logic.dungeon.config;

import java.util.List;

public interface DungeonMonsterGroupCfg {

    Integer getGroupId();

    List<DungeonMonsterCfg> getMonsters();
}
