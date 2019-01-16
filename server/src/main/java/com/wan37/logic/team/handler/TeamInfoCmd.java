package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.team.service.TeamInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组队信息
 */
@Service
class TeamInfoCmd implements GeneralHandler {

    @Autowired
    private TeamInfoExec teamInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        teamInfoExec.exec(msg.getPlayer());
    }
}
