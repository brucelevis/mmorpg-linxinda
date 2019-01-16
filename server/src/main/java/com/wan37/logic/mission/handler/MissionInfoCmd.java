package com.wan37.logic.mission.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mission.service.MissionInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家任务信息
 */
@Service
class MissionInfoCmd implements GeneralHandler {

    @Autowired
    private MissionInfoExec missionInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        missionInfoExec.exec(msg.getPlayer());
    }
}
