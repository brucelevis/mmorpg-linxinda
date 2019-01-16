package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.team.service.TeamAcceptExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 同意加入组队
 */
@Service
class TeamAcceptCmd implements GeneralHandler {

    @Autowired
    private TeamAcceptExec teamAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long teamUid = msg.getParamAsLong(1);
        teamAcceptExec.exec(msg.getPlayer(), teamUid);
    }
}
