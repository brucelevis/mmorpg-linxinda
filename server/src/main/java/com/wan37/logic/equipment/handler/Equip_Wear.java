package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.equipment.service.wear.EquipWearExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Equip_Wear implements GeneralHandler {

    @Autowired
    private EquipWearExec equipWearExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        String[] params = msg.getParams();
        Long uid = Long.parseLong(params[1]);

        equipWearExec.exec(channelId, uid);
    }
}
