package com.wan37.logic.mission.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.mission.service.MissionCommitExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Mission_Commit implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MissionCommitExec missionCommitExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer missionId = msg.getParamAsInt(1);
        missionCommitExec.exec(player, missionId);
    }
}
