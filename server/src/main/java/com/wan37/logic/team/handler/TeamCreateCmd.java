package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.service.TeamCreateExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建组队
 */
@Service
class TeamCreateCmd implements GeneralHandler {

    @Autowired
    private TeamCreateExec teamCreateExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        teamCreateExec.exec(msg.getPlayer());
    }
}
