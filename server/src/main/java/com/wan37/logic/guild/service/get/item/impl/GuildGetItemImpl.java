package com.wan37.logic.guild.service.get.item.impl;

import com.wan37.logic.guild.service.get.item.GuildGetItem;

class GuildGetItemImpl implements GuildGetItem {

    public GuildGetItemImpl(Integer index, int amount) {
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
