package com.wan37.logic.player.service.register.impl;

import com.wan37.logic.player.service.register.PRegisterPlayer;
import com.wan37.util.GeneralNotifySenderUtil;
import io.netty.channel.Channel;

public class PlayerImpl implements PRegisterPlayer {

    public PlayerImpl(String[] params, Channel channel) {
        this.facctionId = Integer.valueOf(params[1]);
        this.name = params[2];
        this.channel = channel;
    }

    @Override
    public Integer getFactionId() {
        return facctionId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void response(String msg) {
        GeneralNotifySenderUtil.send(channel, msg);
    }

    private final Integer facctionId;
    private final String name;

    private final Channel channel;
}
