package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    public void schedule(Scene scene) {
        // 刷新怪物
        sceneMonsterScheduler.schedule(scene);

        // 刷新物品
        sceneItemScheduler.schedule(scene);
    }
}
