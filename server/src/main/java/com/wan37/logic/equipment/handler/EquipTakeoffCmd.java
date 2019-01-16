package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.equipment.service.EquipTakeoffExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 脱装备
 */
@Service
class EquipTakeoffCmd implements GeneralHandler {

    @Autowired
    private EquipTakeoffExec equipTakeoffExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        String[] param = msg.getParams();
        Integer part = Integer.parseInt(param[1]);
        equipTakeoffExec.exec(player, part);
    }
}
