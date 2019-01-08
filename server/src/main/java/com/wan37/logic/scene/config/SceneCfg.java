package com.wan37.logic.scene.config;

import com.wan37.logic.scene.SceneTypeEnum;

import java.util.List;
import java.util.Set;

/**
 * 场景配置表
 *
 * @author linda
 */
public interface SceneCfg {

    /**
     * 场景id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 场景名字
     *
     * @return String
     */
    String getName();

    /**
     * 是否为默认场景
     *
     * @return boolean
     */
    boolean isDefault();

    /**
     * 相邻场景id
     *
     * @return Set<Integer>
     */
    Set<Integer> getNeighbor();

    /**
     * 场景npc
     *
     * @return List<Integer>
     */
    List<Integer> getNpcs();

    /**
     * 场景怪物配置
     *
     * @return List<SceneMonsterCfg>
     */
    List<SceneMonsterCfg> getMonsters();

    /**
     * 可否攻击
     *
     * @return boolean
     */
    boolean canAttack();

    /**
     * 场景类型
     *
     * @return Integer
     * @see SceneTypeEnum#getId()
     */
    Integer getType();
}
