package com.wan37.logic.player.handler;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.OfflineEvent;
import com.wan37.handler.GeneralHandler;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Player_Logout implements GeneralHandler {

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        genernalEventListenersManager.fireEvent(new OfflineEvent(channel));

        channel.writeAndFlush("下线成功\n");
    }
}
