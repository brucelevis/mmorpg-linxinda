package com.wan37.logic.skill.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.skill.service.SkillInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家技能信息
 */
@Service
class SkillInfoCmd implements GeneralHandler {

    @Autowired
    private SkillInfoExec skillInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        skillInfoExec.exec(msg.getPlayer());
    }
}
