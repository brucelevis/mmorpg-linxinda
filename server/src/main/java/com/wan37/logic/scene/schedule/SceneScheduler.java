package com.wan37.logic.scene.schedule;

import com.wan37.logic.scene.base.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    @Autowired
    private SceneMpRecoverScheduler sceneMpRecoverScheduler;

    @Autowired
    private ScenePlayerBuffsScheduler scenePlayerBuffsScheduler;

    @Autowired
    private SceneMonsterBuffsScheduler sceneMonsterBuffsScheduler;

    @Autowired
    private SceneMonsterAiScheduler sceneMonsterAiScheduler;

    @Autowired
    private ScenePlayerReviveScheduler scenePlayerReviveScheduler;

    @Autowired
    private SceneSummoningAiScheduler sceneSummoningAiScheduler;

    public void schedule(Scene scene) {
        // 刷新怪物
        sceneMonsterScheduler.schedule(scene);

        // 刷新物品
        sceneItemScheduler.schedule(scene);

        // 场景mp回复
        sceneMpRecoverScheduler.schedule(scene);

        // 更新玩家buff
        scenePlayerBuffsScheduler.schedule(scene);

        // 更新怪物buff
        sceneMonsterBuffsScheduler.schedule(scene);

        // 怪物自动攻击
        sceneMonsterAiScheduler.schedule(scene);

        // 召唤兽自当攻击
        sceneSummoningAiScheduler.schedule(scene);

        // 玩家复活
        scenePlayerReviveScheduler.schedule(scene);
    }
}
