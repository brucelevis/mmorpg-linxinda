package com.wan37.logic.scene.config;

import java.util.List;
import java.util.Set;

public interface SceneCfg {

    Integer getId();

    boolean isPersonal();

    String getName();

    boolean isDefault();

    Set<Integer> getNeighbor();

    // 场景NPC配置

    // 场景怪物配置
    List<SceneMonsterCfg> getMonsters();
}
