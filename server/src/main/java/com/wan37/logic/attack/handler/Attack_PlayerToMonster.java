package com.wan37.logic.attack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attack.service.AttackPlayerToMonsterExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attack_PlayerToMonster implements GeneralHandler {

    @Autowired
    private AttackPlayerToMonsterExec attackPlayerToMonsterExec;

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

        attackPlayerToMonsterExec.exec(player, skillId, monsterUid);
    }
}
