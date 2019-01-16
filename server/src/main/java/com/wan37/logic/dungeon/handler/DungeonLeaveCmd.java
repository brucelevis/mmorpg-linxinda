package com.wan37.logic.dungeon.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.dungeon.service.DungeonLeaveExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 离开副本
 */
@Service
class DungeonLeaveCmd implements GeneralHandler {

    @Autowired
    private DungeonLeaveExec dungeonLeaveExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        dungeonLeaveExec.exec(msg.getPlayer());
    }
}
