package com.wan37.logic.mail.service.send.impl;

import com.wan37.logic.mail.service.send.ReqSendMail;
import com.wan37.logic.mail.service.send.ReqSendMailItem;
import com.wan37.logic.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class MailImpl implements ReqSendMail {

    public MailImpl(Player player, Long toUid, String title, String content, String items) {
        this.player = player;
        this.toUid = toUid;
        this.title = title;
        this.content = content;
        this.items = items;
    }

    @Override
    public Player getFromPlayer() {
        return player;
    }

    @Override
    public Long getToUid() {
        return toUid;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<ReqSendMailItem> getItems() {
        if ("null".equals(items)) {
            return null;
        }

        return Arrays.stream(items.split(","))
                .map(this::createItem)
                .collect(Collectors.toList());
    }

    private ReqSendMailItem createItem(String s) {
        String[] item = s.split(":");

        Integer index = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);

        return new MailItemImpl(index, amount);
    }

    private final Player player;
    private final Long toUid;
    private final String title;
    private final String content;
    private final String items;
}
