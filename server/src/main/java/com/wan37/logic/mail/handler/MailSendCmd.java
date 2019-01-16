package com.wan37.logic.mail.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.mail.service.send.MailSendExec;
import com.wan37.logic.mail.service.send.ReqSendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MailSendCmd implements GeneralHandler {

    @Autowired
    private MailSendExec mailSendExec;

    @Autowired
    private ReqSendMail.Factory reqSendMailFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        ReqSendMail mail = reqSendMailFactory.create(msg.getPlayer(), msg.getParams());
        mailSendExec.exec(mail);
    }
}
