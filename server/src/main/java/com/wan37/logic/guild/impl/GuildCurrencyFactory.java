package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildCurrencyDb;
import com.wan37.logic.guild.GuildCurrency;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildCurrencyFactory implements GuildCurrency.Factory {

    @Override
    public GuildCurrency create(GuildCurrencyDb currencyDb) {
        return new GuildCurrencyImpl(currencyDb);
    }
}
