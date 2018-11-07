package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.equipment.service.takeoff.EquipTakeoffExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Equip_Takeoff implements GeneralHandler {

    @Autowired
    private EquipTakeoffExec equipTakeoffExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        String[] param = msg.getParams();
        Integer part = Integer.parseInt(param[1]);

        equipTakeoffExec.exec(channelId, part);
    }
}
