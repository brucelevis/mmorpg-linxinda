package com.wan37.logic.league.service.donate.item.impl;

import com.wan37.logic.league.service.donate.item.LDonateItem;
import com.wan37.logic.league.service.donate.item.ReqLDonateItem;

import java.util.List;

class ReqLDonateItemImpl implements ReqLDonateItem {

    public ReqLDonateItemImpl(List<LDonateItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public List<LDonateItem> getDonateItems() {
        return itemList;
    }

    private final List<LDonateItem> itemList;
}
