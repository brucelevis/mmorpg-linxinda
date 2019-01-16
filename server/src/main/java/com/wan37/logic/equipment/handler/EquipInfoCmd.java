package com.wan37.logic.equipment.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.equipment.service.EquipInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 装备栏信息
 */
@Service
class EquipInfoCmd implements GeneralHandler {

    @Autowired
    private EquipInfoExec equipInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        equipInfoExec.exec(msg.getPlayer());
    }
}
