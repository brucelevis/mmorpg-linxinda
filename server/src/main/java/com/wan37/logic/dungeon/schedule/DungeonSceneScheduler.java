package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonSceneScheduler {

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

    @Autowired
    private DungeonExpireScheduler dungeonExpireScheduler;

    @Autowired
    private DungeonMonsterRefreshScheduler dungeonMonsterRefreshScheduler;

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

        // 副本过期
        DungeonScene dungeonScene = (DungeonScene) scene;
        dungeonExpireScheduler.schedule(dungeonScene);

        // 怪物刷新
        dungeonMonsterRefreshScheduler.schedule(dungeonScene);
    }
}
