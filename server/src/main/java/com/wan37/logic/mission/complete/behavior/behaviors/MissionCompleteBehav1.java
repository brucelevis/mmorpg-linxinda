package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_1
 */
@Service
class MissionCompleteBehav1 implements MissionCompleteBehavior {

    @Override
    public void behave(MissionCompleteContext context) {
        // NOOP
    }
}
