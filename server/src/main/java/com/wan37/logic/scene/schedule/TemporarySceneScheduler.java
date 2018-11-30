package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemporarySceneScheduler {

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    @Autowired
    private ScenePlayerBuffsScheduler scenePlayerBuffsScheduler;

    @Autowired
    private SceneMonsterBuffsScheduler sceneMonsterBuffsScheduler;

    @Autowired
    private SceneMonsterAiScheduler sceneMonsterAiScheduler;

    @Autowired
    private ScenePlayerReviveScheduler scenePlayerReviveScheduler;

    public void schedule(AbstractScene scene) {
        // 刷新物品
        sceneItemScheduler.schedule(scene);

        // 更新玩家buff
        scenePlayerBuffsScheduler.schedule(scene);

        // 更新怪物buff
        sceneMonsterBuffsScheduler.schedule(scene);

        // 怪物自动攻击
        sceneMonsterAiScheduler.schedule(scene);

        // 玩家复活
        scenePlayerReviveScheduler.schedule(scene);

        //TODO: 如果过期了，副本结束逻辑
    }
}
