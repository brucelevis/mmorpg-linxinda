package com.wan37.logic.mission.listener;

import com.wan37.event.event.EquipWearEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听穿戴装备事件
 */
@Service
class MissionOnEquipWear implements GeneralEventListener<EquipWearEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(EquipWearEvent equipWearEvent) {
        Player player = equipWearEvent.getPlayer();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.TOTAL_EQUIP_LEVEL.getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> missionCompleteChecker.check(player, m));
    }
}
