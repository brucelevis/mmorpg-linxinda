package com.wan37.logic.attack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attack.service.AttackP2MExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attack_P2M implements GeneralHandler {

    @Autowired
    private AttackP2MExec attackP2MExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        String[] params = msg.getParams();

        Integer skillId = Integer.parseInt(params[1]);
        Long monsterUid = Long.parseLong(params[2]);

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        attackP2MExec.exec(player, skillId, monsterUid);
    }
}
