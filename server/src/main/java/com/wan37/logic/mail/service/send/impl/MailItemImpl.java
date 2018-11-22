package com.wan37.logic.mail.service.send.impl;

import com.wan37.logic.mail.service.send.ReqSendMailItem;

class MailItemImpl implements ReqSendMailItem {

    public MailItemImpl(Integer index, int amount) {
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
