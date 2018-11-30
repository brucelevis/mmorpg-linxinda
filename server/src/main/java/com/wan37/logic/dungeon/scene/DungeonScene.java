package com.wan37.logic.dungeon.scene;

import com.wan37.logic.scene.base.TemporaryScene;
import com.wan37.logic.scene.schedule.TemporarySceneScheduler;

/**
 * 副本场景
 */
public class DungeonScene extends TemporaryScene {

    /**
     * 现阶段怪物组id
     */
    private Integer monsterGroupId;

    private TemporarySceneScheduler temporarySceneScheduler;

    public Integer getMonsterGroupId() {
        return monsterGroupId;
    }

    public void setMonsterGroupId(Integer monsterGroupId) {
        this.monsterGroupId = monsterGroupId;
    }

    public TemporarySceneScheduler getTemporarySceneScheduler() {
        return temporarySceneScheduler;
    }

    public void setTemporarySceneScheduler(TemporarySceneScheduler temporarySceneScheduler) {
        this.temporarySceneScheduler = temporarySceneScheduler;
    }

    @Override
    public void run() {
        try {
            temporarySceneScheduler.schedule(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
