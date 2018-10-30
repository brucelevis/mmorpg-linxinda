package com.wan37.logic.scene.handle;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.scene.service.aoi.SAoiScene;
import com.wan37.logic.scene.service.aoi.SceneAoiExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Scene_Aoi implements GeneralHandler {

    @Autowired
    private SAoiScene.Factory factory;

    @Autowired
    private SceneAoiExec sceneAoiExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        SAoiScene scene = factory.create(msg);
        sceneAoiExec.exec(scene);
    }
}
