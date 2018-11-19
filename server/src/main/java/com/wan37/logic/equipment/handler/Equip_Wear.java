package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.equipment.service.wear.EquipWearExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Equip_Wear implements GeneralHandler {

    @Autowired
    private EquipWearExec equipWearExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        String[] params = msg.getParams();
        Long uid = Long.parseLong(params[1]);

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        equipWearExec.exec(player, uid);
    }
}
