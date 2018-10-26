package com.wan37.logic.player.service.login.impl;

import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.player.service.login.PLoginPlayer;
import io.netty.channel.Channel;

class PlayerImpl implements PLoginPlayer {

    @Override
    public Long getPlayerUid() {
        return null;
    }

    @Override
    public Channel getChannel() {
        return null;
    }

    @Override
    public void response(GeneralResponseDto dto) {

    }
}
