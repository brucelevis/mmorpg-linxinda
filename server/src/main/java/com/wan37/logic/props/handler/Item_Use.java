package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.props.service.ItemUseExec;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Item_Use implements GeneralHandler {

    @Autowired
    private ItemUseExec itemUseExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        String[] params = msg.getParams();
        Integer index = Integer.parseInt(params[1]);

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        itemUseExec.exec(player, index);
    }
}
