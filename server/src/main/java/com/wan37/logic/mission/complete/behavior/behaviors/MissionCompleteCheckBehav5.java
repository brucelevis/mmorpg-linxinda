package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.entity.IPlayerMission;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_5
 */
@Service
class MissionCompleteCheckBehav5 implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        IPlayerMission playerMission = context.getPlayerMission();
        if (playerMission.getProgress() == 0) {
            return;
        }

        context.setResult(true);
    }
}
