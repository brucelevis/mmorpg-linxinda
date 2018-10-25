package com.wan37.logic.player.service.register.impl;

import com.wan37.Utils.MessageSenderFormatter;
import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.player.service.register.PRegisterPlayer;
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
    public void response(GeneralResponseDto dto) {
        channel.writeAndFlush(MessageSenderFormatter.format(dto));
    }

    private final Integer facctionId;
    private final String name;

    private final Channel channel;
}
