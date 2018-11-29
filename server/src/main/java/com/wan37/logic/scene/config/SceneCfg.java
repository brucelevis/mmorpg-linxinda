package com.wan37.logic.scene.config;

import java.util.List;
import java.util.Set;

public interface SceneCfg {

    Integer getId();

    String getName();

    boolean isDefault();

    Set<Integer> getNeighbor();

    List<Integer> getNpcs();

    List<SceneMonsterCfg> getMonsters();

    boolean canAttack();

    Integer getType();
}
