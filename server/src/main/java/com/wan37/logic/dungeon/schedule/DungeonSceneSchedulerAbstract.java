package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.scene.DungeonSceneAbstract;
import com.wan37.logic.scene.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 副本场景定时器
 *
 * @author linda
 */
@Service
public class DungeonSceneSchedulerAbstract extends AbstractGeneralSceneScheduler {

    @Autowired
    private ScenePlayerReviveScheduler scenePlayerReviveScheduler;

    @Autowired
    private DungeonExpireScheduler dungeonExpireScheduler;

    @Autowired
    private DungeonMonsterRefreshScheduler dungeonMonsterRefreshScheduler;

    public void schedule(DungeonSceneAbstract scene) {
        super.schedule(scene);

        // 玩家复活
        scenePlayerReviveScheduler.schedule(scene);

        // 副本过期
        dungeonExpireScheduler.schedule(scene);

        // 副本怪物刷新
        dungeonMonsterRefreshScheduler.schedule(scene);
    }
}
