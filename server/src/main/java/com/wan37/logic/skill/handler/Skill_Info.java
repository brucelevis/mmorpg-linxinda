package com.wan37.logic.skill.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.skill.service.SkillInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Skill_Info implements GeneralHandler {

    @Autowired
    private SkillInfoExec skillInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        skillInfoExec.exec(channelId);
    }
}
