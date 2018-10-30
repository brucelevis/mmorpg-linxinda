package com.wan37.logic.scene.service.switch1;

import com.wan37.server.GeneralReqMsg;

public interface SSwitchScene {

    interface Factory {

        SSwitchScene create(GeneralReqMsg msg);
    }

    Integer getSceneId();

    String getChannelId();
}
