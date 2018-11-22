package com.wan37.logic.mail.service.send.impl;

import com.wan37.logic.mail.service.send.ReqSendMail;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class ReqSendMailFactoryImpl implements ReqSendMail.Factory {

    @Override
    public ReqSendMail create(Player player, String[] params) {
        Long toUid = Long.parseLong(params[1]);
        String title = params[2];
        String content = params[3];
        String items = params[4];

        return new MailImpl(player, toUid, title, content, items);
    }
}
