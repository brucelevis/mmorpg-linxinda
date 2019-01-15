package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.VirtualItemAddEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听添加虚物事件
 */
@Service
class MissionOnVirtualItemAdd implements GeneralEventListener<VirtualItemAddEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(VirtualItemAddEvent virtualItemAddEvent) {
        Player player = virtualItemAddEvent.getPlayer();
        Integer cfgId = virtualItemAddEvent.getCfgId();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.VIRTUAL_ITEM_AMOUNT.getId()))
                .filter(m -> Objects.equals(m.getMissionCfg().getTargetId(), cfgId))
                .filter(m -> !m.canComplete())
                .forEach(m -> missionCompleteChecker.check(player, m));
    }
}
