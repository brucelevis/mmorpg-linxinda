package com.wan37.logic.pk.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.pk.service.PkRejectExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拒绝决斗
 */
@Service
class PkRejectCmd implements GeneralHandler {

    @Autowired
    private PkRejectExec pkRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        pkRejectExec.exec(msg.getPlayer(), uid);
    }
}
