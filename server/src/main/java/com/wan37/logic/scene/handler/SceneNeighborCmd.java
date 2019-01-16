package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.scene.service.SceneNeighborExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 相邻场景信息
 */
@Service
class SceneNeighborCmd implements GeneralHandler {

    @Autowired
    private SceneNeighborExec sceneNeighborExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        sceneNeighborExec.exec(msg.getPlayer());
    }
}
