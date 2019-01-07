package com.wan37.logic.mission.listener;

import com.wan37.event.entity.DungeonCompleteEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听副本完成事件
 */
@Service
class MissionOnDungeonComplete implements GeneralEventListener<DungeonCompleteEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(DungeonCompleteEvent dungeonCompleteEvent) {
        Player player = dungeonCompleteEvent.getPlayer();
        Integer dungeonCfgId = dungeonCompleteEvent.getDungeonCfgId();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.MISSION_TYPE_11.getId()))
                .filter(m -> Objects.equals(m.getMissionCfg().getTargetId(), dungeonCfgId))
                .filter(m -> !m.canComplete())
                .forEach(m -> completeImpl(player, m));
    }

    private void completeImpl(Player player, PlayerMission playerMission) {
        playerMission.setProgress(playerMission.getProgress() + 1);
        missionCompleteChecker.check(player, playerMission);
    }
}
