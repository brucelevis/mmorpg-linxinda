package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaSceneAbstract;
import com.wan37.logic.scene.schedule.AbstractGeneralSceneScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 竞技场场景定时器
 *
 * @author linda
 */
@Service
public class ArenaSceneSchedulerAbstract extends AbstractGeneralSceneScheduler {

    @Autowired
    private ArenaPlayerCheckerScheduler arenaPlayerCheckerScheduler;

    @Autowired
    private ArenaExpireScheduler arenaExpireScheduler;

    public void schedule(ArenaSceneAbstract scene) {
        super.schedule(scene);

        // 检查竞技场玩家情况
        arenaPlayerCheckerScheduler.schedule(scene);

        // 竞技场过期
        arenaExpireScheduler.schedule(scene);
    }
}
