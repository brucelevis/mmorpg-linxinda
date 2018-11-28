package com.wan37.logic.attack.handler;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attack.service.AttackP2PExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attack_P2P implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private AttackP2PExec attackP2PExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player attacker = playerGlobalManager.getPlayerByChannel(channel);
        if (attacker == null) {
            return;
        }

        String[] params = msg.getParams();
        Integer skillId = Integer.parseInt(params[1]);
        Long targetUid = Long.parseLong(params[2]);

        Player target = playerGlobalManager.getPlayerByUid(targetUid);
        if (target == null) {
            throw new GeneralErrorExecption("攻击目标不存在");
        }

        attackP2PExec.exec(attacker, target, skillId);
    }
}
