package com.wan37.logic.mail.service.send;

import com.wan37.logic.player.Player;

import java.util.List;

public interface ReqSendMail {

    interface Factory {

        ReqSendMail create(Player player, String[] params);
    }

    Player getFromPlayer();

    Long getToUid();

    String getTitle();

    String getContent();

    List<ReqSendMailItem> getItems();
}
