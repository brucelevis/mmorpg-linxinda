package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_2
 */
@Service
class MissionCompleteCheckBehavior2 implements MissionCompleteCheckBehavior {

    @Override
    public void behave(MissionCompleteCheckContext context) {
        Player player = context.getPlayer();
        PlayerMission playerMission = context.getPlayerMission();
        if (player.getLevel() < playerMission.getMissionCfg().getArgsAsInt()) {
            // 任务没完成;
            return;
        }

        context.setResult(true);
    }
}
