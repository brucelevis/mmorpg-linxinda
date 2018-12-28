package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.scene.schedule.ScenePlayerBuffsScheduler;
import com.wan37.logic.scene.schedule.SceneSummoningAiScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArenaSceneScheduler {

    @Autowired
    private ScenePlayerBuffsScheduler scenePlayerBuffsScheduler;

    @Autowired
    private ArenaPlayerCheckerScheduler arenaPlayerCheckerScheduler;

    @Autowired
    private ArenaExpireScheduler arenaExpireScheduler;

    @Autowired
    private SceneSummoningAiScheduler sceneSummoningAiScheduler;

    public void schedule(ArenaScene scene) {
        // 更新玩家buff
        scenePlayerBuffsScheduler.schedule(scene);

        // 召唤兽自当攻击
        sceneSummoningAiScheduler.schedule(scene);

        // 检查竞技场玩家情况
        arenaPlayerCheckerScheduler.schedule(scene);

        // 竞技场过期
        arenaExpireScheduler.schedule(scene);
    }
}
