package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    public void schedule(Scene scene) {
        // 怪物相关
        sceneMonsterScheduler.schedule(scene);
    }
}
