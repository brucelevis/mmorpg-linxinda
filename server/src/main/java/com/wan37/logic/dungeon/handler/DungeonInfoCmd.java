package com.wan37.logic.dungeon.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.dungeon.service.DungeonInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 副本信息
 */
@Service
class DungeonInfoCmd implements GeneralHandler {

    @Autowired
    private DungeonInfoExec dungeonInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        dungeonInfoExec.exec(msg.getPlayer());
    }
}
