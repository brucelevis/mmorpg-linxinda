package com.wan37.logic.mail.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mail.service.MailInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MailInfoCmd implements GeneralHandler {

    @Autowired
    private MailInfoExec mailInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        mailInfoExec.exec(msg.getPlayer());
    }
}
