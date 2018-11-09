package com.wan37.logic.player.service.register;

import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;

public interface PRegisterPlayer {

    interface Factory {

        PRegisterPlayer create(GeneralReqMsg msg);
    }

    Integer getFactionId();

    String getName();

    Channel getChannel();

    void response(String msg);
}
