package com.wan37.logic.mission.complete.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteCheckContext;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.mission.MissionTypeEnum#VIRTUAL_ITEM_AMOUNT
 */
@Service
@BehaviorLogic(id = 4)
class MissionAboutVirtualItemAmount implements MissionCompleteCheckBehavior {

    @Autowired
    private ResourceFacade resourceFacade;

    @Override
    public void behave(MissionCompleteCheckContext context) {
        Player player = context.getPlayer();
        PlayerMission playerMission = context.getPlayerMission();
        MissionCfg missionCfg = playerMission.getMissionCfg();

        if (resourceFacade.queryCurrency(missionCfg.getTargetId(), player) < missionCfg.getArgsAsInt()) {
            return;
        }

        context.setResult(true);
    }
}
