package com.wan37.logic.dungeon.scene;

import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.scene.base.TemporaryScene;
import com.wan37.logic.dungeon.schedule.DungeonSceneScheduler;

/**
 * 副本场景
 *
 * @author linda
 */
public class DungeonScene extends TemporaryScene {

    /**
     * 现阶段怪物组id
     */
    private int monsterGroupId;

    private DungeonSceneScheduler dungeonSceneScheduler;

    private DungeonCfg dungeonCfg;

    public int getMonsterGroupId() {
        return monsterGroupId;
    }

    public void setMonsterGroupId(int monsterGroupId) {
        this.monsterGroupId = monsterGroupId;
    }

    public void setDungeonSceneScheduler(DungeonSceneScheduler dungeonSceneScheduler) {
        this.dungeonSceneScheduler = dungeonSceneScheduler;
    }

    public DungeonCfg getDungeonCfg() {
        return dungeonCfg;
    }

    public void setDungeonCfg(DungeonCfg dungeonCfg) {
        this.dungeonCfg = dungeonCfg;
    }

    @Override
    public void run() {
        try {
            dungeonSceneScheduler.schedule(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
