package com.wan37.logic.scene.service.aoi.impl;

import com.wan37.Utils.MessageSenderFormatter;
import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.scene.service.aoi.SAoiScene;
import io.netty.channel.Channel;


class SceneImpl implements SAoiScene {

    public SceneImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void response(GeneralResponseDto dto) {
        channel.writeAndFlush(MessageSenderFormatter.format(dto));
    }

    private final Channel channel;
}
