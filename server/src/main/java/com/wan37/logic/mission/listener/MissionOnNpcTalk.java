package com.wan37.logic.mission.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.NpcTalkEvent;
import com.wan37.logic.mission.MissionTypeEnum;
import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 任务监听npc对话事件
 */
@Service
class MissionOnNpcTalk implements GeneralEventListener<NpcTalkEvent> {

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    @Override
    public void execute(NpcTalkEvent npcTalkEvent) {
        Player player = npcTalkEvent.getPlayer();
        Integer npcId = npcTalkEvent.getNpcId();

        player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getType(), MissionTypeEnum.NPC_TALK.getId()))
                .filter(m -> Objects.equals(m.getMissionCfg().getTargetId(), npcId))
                .filter(m -> !m.canComplete())
                .forEach(m -> completeImpl(player, m));
    }

    private void completeImpl(Player player, PlayerMission playerMission) {
        // npc对话进度
        playerMission.setProgress(1);
        missionCompleteChecker.check(player, playerMission);
    }
}
