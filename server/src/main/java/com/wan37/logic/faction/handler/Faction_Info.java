package com.wan37.logic.faction.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.faction.service.FactionInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Faction_Info implements GeneralHandler {

    @Autowired
    private FactionInfoExec factionInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        factionInfoExec.exec(msg.getChannel());
    }
}
