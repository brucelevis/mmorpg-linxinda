package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#LEVEL_UP
 */
@Service
@BehaviorLogic(id = 2)
class MissionAboutLevelUp implements MissionCompleteCheckBehavior {

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
