package com.wan37.logic.guild.service.get.item.impl;

import com.wan37.logic.guild.service.get.item.GuildGetItem;
import com.wan37.logic.guild.service.get.item.ReqGuildGetItem;

import java.util.List;

class ReqGuildGetItemImpl implements ReqGuildGetItem {

    public ReqGuildGetItemImpl(List<GuildGetItem> items) {
        this.items = items;
    }

    @Override
    public List<GuildGetItem> getItems() {
        return items;
    }

    private final List<GuildGetItem> items;
}
