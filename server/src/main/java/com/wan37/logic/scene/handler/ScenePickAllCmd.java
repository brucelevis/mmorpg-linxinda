package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.scene.service.ScenePickAllExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 场景一键捡取
 */
@Service
class ScenePickAllCmd implements GeneralHandler {

    @Autowired
    private ScenePickAllExec scenePickAllExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        scenePickAllExec.exec(msg.getPlayer());
    }
}
