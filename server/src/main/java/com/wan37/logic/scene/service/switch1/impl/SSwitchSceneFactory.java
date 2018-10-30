package com.wan37.logic.scene.service.switch1.impl;

import com.wan37.logic.scene.service.switch1.SSwitchScene;
import com.wan37.server.GeneralReqMsg;
import org.springframework.stereotype.Service;

@Service
class SSwitchSceneFactory implements SSwitchScene.Factory {

    @Override
    public SSwitchScene create(GeneralReqMsg msg) {
        return new SceneImpl(msg);
    }
}
