package com.wan37.logic.attr.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attr.service.AttrInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attr_Info implements GeneralHandler {

    @Autowired
    private AttrInfoExec attrInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        attrInfoExec.exec(msg.getChannel());
    }
}
