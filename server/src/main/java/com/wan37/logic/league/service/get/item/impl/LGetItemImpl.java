package com.wan37.logic.league.service.get.item.impl;

import com.wan37.logic.league.service.get.item.LGetItem;

class LGetItemImpl implements LGetItem {

    public LGetItemImpl(Integer index, int amount) {
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
