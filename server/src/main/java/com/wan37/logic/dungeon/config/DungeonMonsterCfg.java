package com.wan37.logic.dungeon.config;

import com.wan37.logic.monster.config.MonsterCfg;

/**
 * 副本怪物配置表
 *
 * @author linda
 */
public interface DungeonMonsterCfg {

    /**
     * 怪物id
     *
     * @return Integer
     * @see MonsterCfg#getId()
     */
    Integer getMonsterId();

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();
}
