package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.scene.schedule.GeneralSceneScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArenaSceneScheduler extends GeneralSceneScheduler {

    @Autowired
    private ArenaPlayerCheckerScheduler arenaPlayerCheckerScheduler;

    @Autowired
    private ArenaExpireScheduler arenaExpireScheduler;

    public void schedule(ArenaScene scene) {
        super.schedule(scene);

        // 检查竞技场玩家情况
        arenaPlayerCheckerScheduler.schedule(scene);

        // 竞技场过期
        arenaExpireScheduler.schedule(scene);
    }
}