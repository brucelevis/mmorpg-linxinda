package com.wan37.logic.scene.item;

import com.wan37.logic.monster.config.MonsterCfg;

import java.util.List;

public interface SceneItem {

    interface Factory {

        List<SceneItem> create(MonsterCfg monsterCfg);
    }

    Long getUid();

    Integer getCfgId();

    int getAmount();

    String getName();

    long getExpireTime();
}
