package com.wan37.logic.attack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attack.service.AttackPlayerToMonsterExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attack_PlayerToMonster implements GeneralHandler {

    @Autowired
    private AttackPlayerToMonsterExec attackPlayerToMonsterExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        String[] params = msg.getParams();

        Integer skillId = Integer.parseInt(params[1]);
        Long monsterUid = Long.parseLong(params[2]);

        attackPlayerToMonsterExec.exec(channelId, skillId, monsterUid);
    }
}
