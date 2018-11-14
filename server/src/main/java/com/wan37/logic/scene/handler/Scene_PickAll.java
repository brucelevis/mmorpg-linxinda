package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.scene.service.pick.ScenePickAllExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Scene_PickAll implements GeneralHandler {

    @Autowired
    private ScenePickAllExec scenePickAllExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        scenePickAllExec.exec(channelId);
    }
}
