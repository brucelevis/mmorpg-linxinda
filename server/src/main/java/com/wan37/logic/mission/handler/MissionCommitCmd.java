package com.wan37.logic.mission.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mission.service.MissionCommitExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提交任务
 */
@Service
class MissionCommitCmd implements GeneralHandler {

    @Autowired
    private MissionCommitExec missionCommitExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer missionId = msg.getParamAsInt(1);
        missionCommitExec.exec(msg.getPlayer(), missionId);
    }
}
