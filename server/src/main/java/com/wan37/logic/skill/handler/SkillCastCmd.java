package com.wan37.logic.skill.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.skill.service.SkillCastExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技能施放
 */
@Service
class SkillCastCmd implements GeneralHandler {

    @Autowired
    private SkillCastExec skillCastExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer skillId = msg.getParamAsInt(1);
        Long targetUid = msg.getParamAsLong(2);

        skillCastExec.exec(msg.getPlayer(), skillId, targetUid);
    }
}
