package com.wan37.logic.player.service.login.impl;

import com.wan37.Utils.MessageSenderFormatter;
import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.player.service.login.PLoginPlayer;
import io.netty.channel.Channel;

class PlayerImpl implements PLoginPlayer {

    public PlayerImpl(String[] params, Channel channel) {
        this.playerUid = Long.parseLong(params[1]);
        this.channel = channel;
    }

    @Override
    public Long getPlayerUid() {
        return playerUid;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void response(GeneralResponseDto dto) {
        channel.writeAndFlush(MessageSenderFormatter.format(dto));
    }

    private final Long playerUid;
    private final Channel channel;
}
