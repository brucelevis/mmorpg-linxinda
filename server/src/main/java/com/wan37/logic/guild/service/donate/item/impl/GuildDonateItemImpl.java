package com.wan37.logic.guild.service.donate.item.impl;

import com.wan37.logic.guild.service.donate.item.GuildDonateItem;

class GuildDonateItemImpl implements GuildDonateItem {

    public GuildDonateItemImpl(Integer index, int amount) {
        this.index = index;
        this.amount = amount;
    }

    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    private final Integer index;
    private final int amount;
}
