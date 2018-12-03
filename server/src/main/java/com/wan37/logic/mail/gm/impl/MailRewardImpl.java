package com.wan37.logic.mail.gm.impl;

import com.wan37.logic.mail.gm.GmMailReward;

class MailRewardImpl implements GmMailReward {

    public MailRewardImpl(Integer id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    private final Integer id;
    private final long amount;
}
