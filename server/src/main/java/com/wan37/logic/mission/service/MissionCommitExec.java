package com.wan37.logic.mission.service;

import com.wan37.config.ConfigLoader;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.mission.complete.MissionCompleteHandler;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.Mission;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class MissionCommitExec {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private MissionCompleteHandler missionCompleteHandler;

    public void exec(Player player, Integer missionId) {
        Mission mission = player.getMission();
        if (!mission.isProceeding(missionId)) {
            throw new GeneralErrorException("没在进行中的任务");
        }

        PlayerMission playerMission = mission.getPlayerMission(missionId);
        MissionCfg missionCfg = playerMission.getMissionCfg();

        String npcName = configLoader.loadName(NpcCfg.class, missionCfg.getNpcId());
        if (!playerMission.canComplete()) {
            // 未完成
            String msg = String.format("【%s】 %s", npcName, missionCfg.getProceedTip());
            player.syncClient(msg);
            return;
        }

        missionCompleteHandler.handle(player, playerMission);
    }
}
