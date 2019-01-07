package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.TradeSuccessEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听交易成功事件
 */
@Service
class MissionOnTradeSuccess implements GeneralEventListener<TradeSuccessEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(TradeSuccessEvent tradeSuccessEvent) {
        Player player = tradeSuccessEvent.getPlayer();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_9.getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> completeImpl(player, m));
    }

    private void completeImpl(Player player, PlayerMission playerMission) {
        playerMission.setProgress(1);
        missionCompleteChecker.check(player, playerMission);
    }
}
