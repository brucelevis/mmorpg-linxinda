package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.PlayerMission;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#FIRST_JOIN_GUILD
 */
@Service
@BehaviorLogic(id = 8)
class MissionAboutFirstJoinGuild implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        PlayerMission playerMission = context.getPlayerMission();
        if (playerMission.getProgress() == 0) {
            return;
        }

        context.setResult(true);
    }
}
