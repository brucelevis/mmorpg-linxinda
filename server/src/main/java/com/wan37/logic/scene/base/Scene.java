package com.wan37.logic.scene.base;

import com.wan37.logic.scene.schedule.SceneScheduler;

/**
 * 普通场景
 */
public class Scene extends AbstractScene {

    /**
     * 上次场景恢复mp触发时间
     */
    private volatile long lastRecoverMpTime;

    private SceneScheduler sceneScheduler;

    public long getLastRecoverMpTime() {
        return lastRecoverMpTime;
    }

    public void setLastRecoverMpTime(long lastRecoverMpTime) {
        this.lastRecoverMpTime = lastRecoverMpTime;
    }

    public SceneScheduler getSceneScheduler() {
        return sceneScheduler;
    }

    public void setSceneScheduler(SceneScheduler sceneScheduler) {
        this.sceneScheduler = sceneScheduler;
    }

    @Override
    public void run() {
        try {
            sceneScheduler.schedule(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
