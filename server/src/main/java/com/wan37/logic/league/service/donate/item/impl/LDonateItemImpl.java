package com.wan37.logic.league.service.donate.item.impl;

import com.wan37.logic.league.service.donate.item.LDonateItem;

class LDonateItemImpl implements LDonateItem {

    public LDonateItemImpl(Integer index, int amount) {
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
