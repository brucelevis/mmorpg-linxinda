package com.wan37.logic.scene.service.aoi.impl;

import com.wan37.logic.scene.service.aoi.SAoiScene;
import com.wan37.server.GeneralReqMsg;
import org.springframework.stereotype.Service;

@Service
class SAoiSceneFactory implements SAoiScene.Factory {

    @Override
    public SAoiScene create(GeneralReqMsg msg) {
        return new SceneImpl(msg.getChannel());
    }
}
