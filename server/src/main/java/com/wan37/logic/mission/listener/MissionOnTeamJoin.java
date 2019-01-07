package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.TeamJoinEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务就监听加入组队事件
 */
@Service
class MissionOnTeamJoin implements GeneralEventListener<TeamJoinEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(TeamJoinEvent teamJoinEvent) {
        Player player = teamJoinEvent.getPlayer();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_7.getId()))
                .filter(m -> !m.canComplete())
                .forEach(m -> completeImpl(player, m));
    }

    private void completeImpl(Player player, PlayerMission playerMission) {
        playerMission.setProgress(1);
        missionCompleteChecker.check(player, playerMission);
    }
}
