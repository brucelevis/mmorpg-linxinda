package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.props.service.add.ItemAddExec;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Item_Add implements GeneralHandler {

    @Autowired
    private ItemAddExec itemAddExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        String[] params = msg.getParams();
        Integer cfgId = Integer.parseInt(params[1]);
        long amount = Long.parseLong(params[2]);
        Channel channel = msg.getChannel();

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        itemAddExec.exec(cfgId, amount, player);
    }
}
