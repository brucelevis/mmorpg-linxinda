package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.equipment.service.EquipTakeoffExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 脱装备
 */
@Service
class EquipTakeoffCmd implements GeneralHandler {

    @Autowired
    private EquipTakeoffExec equipTakeoffExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer part = msg.getParamAsInt(1);
        equipTakeoffExec.exec(msg.getPlayer(), part);
    }
}
