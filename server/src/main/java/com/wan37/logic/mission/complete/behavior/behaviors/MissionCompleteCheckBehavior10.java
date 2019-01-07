package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.Mission;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_10
 */
@Service
class MissionCompleteCheckBehavior10 implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        Mission mission = context.getPlayer().getMission();
        MissionCfg missionCfg = context.getPlayerMission().getMissionCfg();
        if (!allComplete(missionCfg, mission)) {
            return;
        }

        context.setResult(true);
    }

    private boolean allComplete(MissionCfg missionCfg, Mission mission) {
        return missionCfg.getArgsAsIntSet().stream()
                .allMatch(mission::hadCompleted);
    }
}
