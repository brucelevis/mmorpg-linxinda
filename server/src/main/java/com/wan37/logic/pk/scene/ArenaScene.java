package com.wan37.logic.pk.scene;

import com.wan37.logic.pk.schedule.ArenaSceneScheduler;
import com.wan37.logic.scene.base.TemporaryScene;

/**
 * 竞技场场景
 */
public class ArenaScene extends TemporaryScene {

    private ArenaSceneScheduler arenaSceneScheduler;

    public ArenaSceneScheduler getArenaSceneScheduler() {
        return arenaSceneScheduler;
    }

    public void setArenaSceneScheduler(ArenaSceneScheduler arenaSceneScheduler) {
        this.arenaSceneScheduler = arenaSceneScheduler;
    }

    @Override
    public void run() {
        try {
            arenaSceneScheduler.schedule(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
