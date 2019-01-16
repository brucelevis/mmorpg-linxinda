package com.wan37.logic.mail.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mail.service.MailReceiveExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MailReceiveCmd implements GeneralHandler {

    @Autowired
    private MailReceiveExec mailReceiveExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long id = msg.getParamAsLong(1);
        mailReceiveExec.exec(msg.getPlayer(), id);
    }
}
