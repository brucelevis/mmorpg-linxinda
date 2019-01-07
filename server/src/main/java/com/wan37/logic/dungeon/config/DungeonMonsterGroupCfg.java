package com.wan37.logic.dungeon.config;

import java.util.List;

/**
 * 副本怪物组配置表
 *
 * @author linda
 */
public interface DungeonMonsterGroupCfg {

    /**
     * 怪物组id
     *
     * @return Integer
     */
    Integer getGroupId();

    /**
     * 副本怪物配置
     *
     * @return List<DungeonMonsterCfg>
     */
    List<DungeonMonsterCfg> getMonsters();
}
