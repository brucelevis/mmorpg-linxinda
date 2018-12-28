package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.scene.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonSceneScheduler extends GeneralSceneScheduler {

    @Autowired
    private ScenePlayerReviveScheduler scenePlayerReviveScheduler;

    @Autowired
    private DungeonExpireScheduler dungeonExpireScheduler;

    @Autowired
    private DungeonMonsterRefreshScheduler dungeonMonsterRefreshScheduler;

    public void schedule(DungeonScene scene) {
        super.schedule(scene);

        // 玩家复活
        scenePlayerReviveScheduler.schedule(scene);

        // 副本过期
        dungeonExpireScheduler.schedule(scene);

        // 副本怪物刷新
        dungeonMonsterRefreshScheduler.schedule(scene);
    }
}
