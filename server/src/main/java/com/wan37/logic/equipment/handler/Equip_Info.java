package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.equipment.service.info.EquipInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Equip_Info implements GeneralHandler {

    @Autowired
    private EquipInfoExec equipInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        equipInfoExec.exec(channelId);
    }
}
