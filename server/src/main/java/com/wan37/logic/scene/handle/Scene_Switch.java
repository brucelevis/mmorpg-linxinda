package com.wan37.logic.scene.handle;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.scene.service.switch1.SSwitchScene;
import com.wan37.logic.scene.service.switch1.SceneSwitchExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Scene_Switch implements GeneralHandler {

    @Autowired
    private SceneSwitchExec sceneSwitchExec;

    @Autowired
    private SSwitchScene.Factory factory;

    @Override
    public void handle(GeneralReqMsg msg) {
        SSwitchScene scene = factory.create(msg);
        sceneSwitchExec.exec(scene);
    }
}
