package com.wan37.logic.pk.scene;

import com.wan37.logic.pk.schedule.ArenaSceneSchedulerAbstract;
import com.wan37.logic.scene.base.AbstractTemporaryScene;

/**
 * 竞技场场景
 *
 * @author linda
 */
public class ArenaSceneAbstract extends AbstractTemporaryScene {

    private ArenaSceneSchedulerAbstract arenaSceneScheduler;

    public void setArenaSceneScheduler(ArenaSceneSchedulerAbstract arenaSceneScheduler) {
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
