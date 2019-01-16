package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.scene.service.SceneAoiExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 场景信息
 */
@Service
class SceneAoiCmd implements GeneralHandler {

    @Autowired
    private SceneAoiExec sceneAoiExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        sceneAoiExec.exec(msg.getPlayer());
    }
}
