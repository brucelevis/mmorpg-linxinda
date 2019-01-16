package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.team.service.TeamRejectExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拒绝加入组队
 */
@Service
class TeamRejectCmd implements GeneralHandler {

    @Autowired
    private TeamRejectExec teamRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        teamRejectExec.exec(msg.getPlayer(), uid);
    }
}
