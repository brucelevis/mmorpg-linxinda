package com.wan37.logic.league.service.get.item.impl;

import com.wan37.logic.league.service.get.item.LGetItem;
import com.wan37.logic.league.service.get.item.ReqLGetItem;

import java.util.List;

class ReqLGetItemImpl implements ReqLGetItem {

    public ReqLGetItemImpl(List<LGetItem> items) {
        this.items = items;
    }

    @Override
    public List<LGetItem> getItems() {
        return items;
    }

    private final List<LGetItem> items;
}
