package com.wan37.logic.mission.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mission.service.accept.MissionAcceptExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接受任务
 */
@Service
class MissionAcceptCmd implements GeneralHandler {

    @Autowired
    private MissionAcceptExec missionAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer id = msg.getParamAsInt(1);
        missionAcceptExec.exec(msg.getPlayer(), id);
    }
}
