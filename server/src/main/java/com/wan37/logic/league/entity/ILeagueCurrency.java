package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueCurrencyDb;

public interface ILeagueCurrency {

    interface Factory {

        ILeagueCurrency create(LeagueCurrencyDb currencyDb);
    }

    LeagueCurrencyDb getLCurrencyDb();

    Integer getCfgId();

    long getAmount();

    void setAmount(long amount);
}
