package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.logic.mission.complete.MissionCompleteHandler;
import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#MISSION_TYPE_4
 */
@Service
class MissionCompleteBehav4 implements MissionCompleteBehavior {

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private MissionCompleteHandler missionCompleteHandler;

    @Override
    public void behave(MissionCompleteContext context) {
        Player player = context.getPlayer();
        IPlayerMission playerMission = context.getPlayerMission();
        MissionCfg missionCfg = playerMission.getMissionCfg();

        if (resourceFacade.queryCurrency(missionCfg.getTargetId(), player) < missionCfg.getArgs()) {
            return;
        }

        // 设置任务可以完成
        playerMission.setCanComplete(true);

        String msg = String.format("[%s]任务完成", missionCfg.getName());
        player.syncClient(msg);

        if (missionCfg.isAutoCommit()) {
            missionCompleteHandler.handle(player, playerMission);
        }
    }
}
