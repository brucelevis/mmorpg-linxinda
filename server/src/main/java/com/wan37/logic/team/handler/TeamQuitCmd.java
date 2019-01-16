package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.team.service.TeamQuitExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退出组队
 */
@Service
class TeamQuitCmd implements GeneralHandler {

    @Autowired
    private TeamQuitExec teamQuitExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        teamQuitExec.exec(msg.getPlayer());
    }
}
