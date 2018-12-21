package com.wan37.logic.mission.listener;

import com.wan37.behavior.BehaviorManager;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.LevelUpEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.behavior.MissionCompleteBehavior;
import com.wan37.logic.mission.complete.behavior.MissionCompleteContext;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class MissionOnLevelUp implements GeneralEventListener<LevelUpEvent> {

    @Autowired
    private BehaviorManager behaviorManager;

    @Override
    public void execute(LevelUpEvent levelUpEvent) {
        Player player = levelUpEvent.getPlayer();
        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_2.getId()))
                .forEach(m -> completeImpl(player, m));
    }

    private void completeImpl(Player player, IPlayerMission playerMission) {
        MissionCompleteBehavior behavior = (MissionCompleteBehavior) behaviorManager.get(
                MissionCompleteBehavior.class, playerMission.getMissionCfg().getType());
        behavior.behave(new MissionCompleteContext(player, playerMission));
    }
}
