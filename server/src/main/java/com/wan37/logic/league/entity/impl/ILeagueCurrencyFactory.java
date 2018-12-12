package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueCurrencyDb;
import com.wan37.logic.league.entity.ILeagueCurrency;
import org.springframework.stereotype.Service;

@Service
public class ILeagueCurrencyFactory implements ILeagueCurrency.Factory {

    @Override
    public ILeagueCurrency create(LeagueCurrencyDb currencyDb) {
        return new ILeagueCurrencyImpl(currencyDb);
    }
}
