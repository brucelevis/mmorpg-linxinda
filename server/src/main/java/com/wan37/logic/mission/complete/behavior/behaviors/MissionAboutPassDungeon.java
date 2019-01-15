package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.PlayerMission;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#PASS_DUNGEON
 */
@Service
@BehaviorLogic(id = 11)
class MissionAboutPassDungeon implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        PlayerMission playerMission = context.getPlayerMission();
        MissionCfg missionCfg = playerMission.getMissionCfg();
        if (playerMission.getProgress() < missionCfg.getArgsAsInt()) {
            return;
        }

        context.setResult(true);
    }
}
