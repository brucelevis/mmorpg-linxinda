package com.wan37.logic.mission.complete;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionCompleteChecker {

    @Autowired
    private BehaviorManager behaviorManager;

    public void check(Player player, IPlayerMission playerMission) {
        MissionCompleteBehavior behavior = (MissionCompleteBehavior) behaviorManager.get(
                MissionCompleteBehavior.class, playerMission.getMissionCfg().getType());
        behavior.behave(new MissionCompleteContext(player, playerMission));
    }
}
