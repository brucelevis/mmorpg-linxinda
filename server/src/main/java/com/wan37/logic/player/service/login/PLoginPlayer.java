package com.wan37.logic.player.service.login;

import com.wan37.common.GeneralResponseDto;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;

public interface PLoginPlayer {

    interface Factory {

        PLoginPlayer create(GeneralReqMsg msg);
    }

    Long getPlayerUid();

    Channel getChannel();

    void response(GeneralResponseDto dto);
}
