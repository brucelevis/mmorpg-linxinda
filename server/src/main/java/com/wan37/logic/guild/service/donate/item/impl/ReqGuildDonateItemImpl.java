package com.wan37.logic.guild.service.donate.item.impl;

import com.wan37.logic.guild.service.donate.item.GuildDonateItem;
import com.wan37.logic.guild.service.donate.item.ReqGuildDonateItem;

import java.util.List;

class ReqGuildDonateItemImpl implements ReqGuildDonateItem {

    public ReqGuildDonateItemImpl(List<GuildDonateItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public List<GuildDonateItem> getDonateItems() {
        return itemList;
    }

    private final List<GuildDonateItem> itemList;
}
