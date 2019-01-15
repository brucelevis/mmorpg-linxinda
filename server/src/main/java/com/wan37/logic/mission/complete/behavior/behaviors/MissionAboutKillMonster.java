package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.PlayerMission;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#KILL_MONSTER
 */
@Service
@BehaviorLogic(id = 1)
class MissionAboutKillMonster implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        PlayerMission playerMission = context.getPlayerMission();
        if (playerMission.getProgress() < playerMission.getMissionCfg().getArgsAsInt()) {
            // 任务没完成;
            return;
        }

        context.setResult(true);
    }
}
