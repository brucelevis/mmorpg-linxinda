package com.wan37.logic.dungeon.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.dungeon.service.DungeonEnterExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 副本进入
 */
@Service
class DungeonEnterCmd implements GeneralHandler {

    @Autowired
    private DungeonEnterExec dungeonEnterExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer dungeonId = msg.getParamAsInt(1);
        dungeonEnterExec.exec(msg.getPlayer(), dungeonId);
    }
}
