package com.wan37.logic.scene.service.switch1.impl;

import com.wan37.logic.scene.service.switch1.SSwitchScene;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;

class SceneImpl implements SSwitchScene {

    public SceneImpl(GeneralReqMsg msg) {
        this.sceneId = Integer.parseInt(msg.getParams()[1]);
        this.channel = msg.getChannel();
    }

    @Override
    public Integer getSceneId() {
        return sceneId;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    private final Integer sceneId;
    private final Channel channel;
}
