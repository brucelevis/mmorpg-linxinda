package com.wan37.logic.pk.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.pk.service.PkAcceptExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 同意决斗
 */
@Service
class PkAcceptCmd implements GeneralHandler {

    @Autowired
    private PkAcceptExec pkAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        pkAcceptExec.exec(msg.getPlayer(), uid);
    }
}
