package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.team.service.TeamDissolveExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组队解散
 */
@Service
class TeamDissolveCmd implements GeneralHandler {

    @Autowired
    private TeamDissolveExec teamDissolveExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        teamDissolveExec.exec(msg.getPlayer());
    }
}
