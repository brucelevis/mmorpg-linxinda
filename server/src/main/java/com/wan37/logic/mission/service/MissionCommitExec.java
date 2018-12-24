package com.wan37.logic.mission.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.mission.complete.MissionCompleteHandler;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionCommitExec {

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private MissionCompleteHandler missionCompleteHandler;

    public void exec(Player player, Integer missionId) {
        IMission iMission = player.getMission();
        if (!iMission.isProceeding(missionId)) {
            throw new GeneralErrorExecption("没在进行中的任务");
        }

        IPlayerMission playerMission = iMission.getPlayerMission(missionId);
        MissionCfg missionCfg = playerMission.getMissionCfg();

        String npcName = npcCfgLoader.loadName(missionCfg.getNpcId());
        if (!playerMission.canComplete()) {
            // 未完成
            String msg = String.format("【%s】 %s", npcName, missionCfg.getProceedTip());
            player.syncClient(msg);
            return;
        }

        missionCompleteHandler.handle(player, playerMission);
    }
}
