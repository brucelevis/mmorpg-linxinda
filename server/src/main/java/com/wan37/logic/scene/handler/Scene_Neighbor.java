package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.scene.service.neighbor.SceneNeighborExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Scene_Neighbor implements GeneralHandler {

    @Autowired
    private SceneNeighborExec sceneNeighborExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        sceneNeighborExec.exec(channelId);
    }
}
