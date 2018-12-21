package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_5
 */
@Service
class MissionCompleteBehav5 implements MissionCompleteBehavior {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void behave(MissionCompleteContext context) {
        Player player = context.getPlayer();
        IPlayerMission playerMission = context.getPlayerMission();
        if (playerMission.getProgress() == 0) {
            return;
        }

        // 设置任务可以完成
        playerMission.setCanComplete(true);

        if (playerGlobalManager.isOnline(player.getUid())) {
            String msg = String.format("[%s]任务完成", playerMission.getMissionCfg().getName());
            player.syncClient(msg);
        }
    }
}
