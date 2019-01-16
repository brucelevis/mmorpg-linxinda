package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.scene.service.SceneSwitchExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 场景跳转
 */
@Service
class SceneSwitchCmd implements GeneralHandler {

    @Autowired
    private SceneSwitchExec sceneSwitchExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer sceneId = msg.getParamAsInt(1);
        sceneSwitchExec.exec(msg.getPlayer(), sceneId);
    }
}
