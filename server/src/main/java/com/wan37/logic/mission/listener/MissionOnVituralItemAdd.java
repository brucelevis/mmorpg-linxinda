package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.VirtualItemAddEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class MissionOnVituralItemAdd implements GeneralEventListener<VirtualItemAddEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(VirtualItemAddEvent virtualItemAddEvent) {
        Player player = virtualItemAddEvent.getPlayer();
        Integer cfgId = virtualItemAddEvent.getCfgId();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_4.getId()))
                .filter(m -> Objects.equals(m.getMissionCfg().getTargetId(), cfgId))
                .filter(m -> !m.canComplete())
                .forEach(m -> missionCompleteChecker.check(player, m));
    }
}