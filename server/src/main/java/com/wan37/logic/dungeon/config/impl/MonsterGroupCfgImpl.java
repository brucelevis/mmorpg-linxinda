package com.wan37.logic.dungeon.config.impl;

import com.wan37.logic.dungeon.config.DungeonMonsterCfg;
import com.wan37.logic.dungeon.config.DungeonMonsterGroupCfg;

import java.util.List;

class MonsterGroupCfgImpl implements DungeonMonsterGroupCfg {

    public MonsterGroupCfgImpl(Integer groupId, List<DungeonMonsterCfg> monsterGroups) {
        this.groupId = groupId;
        this.monsterGroups = monsterGroups;
    }

    @Override
    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public List<DungeonMonsterCfg> getMonsters() {
        return monsterGroups;
    }

    private final Integer groupId;
    private final List<DungeonMonsterCfg> monsterGroups;
}
