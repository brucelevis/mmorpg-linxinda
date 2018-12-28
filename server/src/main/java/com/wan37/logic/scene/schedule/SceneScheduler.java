package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.base.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneScheduler extends GeneralSceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    @Autowired
    private SceneMpRecoverScheduler sceneMpRecoverScheduler;

    @Autowired
    private ScenePlayerReviveScheduler scenePlayerReviveScheduler;

    public void schedule(Scene scene) {
        super.schedule(scene);

        // 刷新怪物
        sceneMonsterScheduler.schedule(scene);

        // 普通场景mp回复
        sceneMpRecoverScheduler.schedule(scene);

        // 玩家复活
        scenePlayerReviveScheduler.schedule(scene);
    }
}
