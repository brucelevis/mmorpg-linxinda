package com.wan37.logic.mission.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.mission.service.MissionAcceptExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Mission_Accept implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MissionAcceptExec missionAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer id = msg.getParamAsInt(1);
        missionAcceptExec.exec(player, id);
    }
}
