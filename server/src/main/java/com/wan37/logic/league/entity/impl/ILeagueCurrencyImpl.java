package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueCurrencyDb;
import com.wan37.logic.league.entity.ILeagueCurrency;

class ILeagueCurrencyImpl implements ILeagueCurrency {

    public ILeagueCurrencyImpl(LeagueCurrencyDb leagueCurrencyDb) {
        this.leagueCurrencyDb = leagueCurrencyDb;
    }

    @Override
    public LeagueCurrencyDb getLCurrencyDb() {
        return leagueCurrencyDb;
    }

    @Override
    public Integer getCfgId() {
        return leagueCurrencyDb.getCfgId();
    }

    @Override
    public long getAmount() {
        return leagueCurrencyDb.getAmount();
    }

    @Override
    public void setAmount(long amount) {
        leagueCurrencyDb.setAmount(amount);
    }

    private final LeagueCurrencyDb leagueCurrencyDb;
}
