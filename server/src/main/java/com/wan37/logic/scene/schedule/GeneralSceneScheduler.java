package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class GeneralSceneScheduler {

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    @Autowired
    private SceneFightingUnitBuffsScheduler sceneFightingUnitBuffsScheduler;

    @Autowired
    private SceneFightingUnitAiScheduler sceneFightingUnitAiScheduler;

    protected void schedule(AbstractScene scene) {
        // 刷新地上物品
        sceneItemScheduler.schedule(scene);

        // 更新buff
        sceneFightingUnitBuffsScheduler.schedule(scene);

        // 怪物及召唤兽AI
        sceneFightingUnitAiScheduler.schedule(scene);
    }
}
