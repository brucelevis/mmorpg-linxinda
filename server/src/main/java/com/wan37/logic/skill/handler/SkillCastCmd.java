package com.wan37.logic.skill.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.skill.service.SkillCastExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技能施放
 */
@Service
class SkillCastCmd implements GeneralHandler {

    @Autowired
    private SkillCastExec skillCastExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer skillId = msg.getParamAsInt(1);
        Long targetUid = msg.getParamAsLong(2);

        skillCastExec.exec(player, skillId, targetUid);
    }
}
