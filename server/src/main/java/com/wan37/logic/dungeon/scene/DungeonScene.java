package com.wan37.logic.dungeon.scene;

import com.wan37.logic.scene.base.TemporaryScene;

/**
 * 副本场景
 */
public class DungeonScene extends TemporaryScene {

    /**
     * 现阶段怪物组id
     */
    private Integer monsterGroupId;

    public Integer getMonsterGroupId() {
        return monsterGroupId;
    }

    public void setMonsterGroupId(Integer monsterGroupId) {
        this.monsterGroupId = monsterGroupId;
    }

    @Override
    public void run() {
        // NOOP
    }
}
