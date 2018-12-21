package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_2
 */
@Service
class MissionCompleteBehav2 implements MissionCompleteBehavior {

    @Override
    public void behave(MissionCompleteContext context) {
        Player player = context.getPlayer();
        IPlayerMission playerMission = context.getPlayerMission();
        MissionCfg missionCfg = playerMission.getMissionCfg();

        if (player.getLevel() < missionCfg.getArgs()) {
            // 任务没完成
            return;
        }

        // 设置任务可以完成
        playerMission.setCanComplete(true);

        String msg = String.format("[%s]任务完成", missionCfg.getName());
        player.syncClient(msg);
    }
}
