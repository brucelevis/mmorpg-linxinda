package com.wan37.logic.scene.config;

import com.wan37.logic.monster.config.MonsterCfg;

/**
 * 场景怪物配置
 *
 * @author linda
 */
public interface SceneMonsterCfg {

    /**
     * 怪物id
     *
     * @return Integer
     * @see MonsterCfg#getId()
     */
    Integer getCfgId();

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();
}
