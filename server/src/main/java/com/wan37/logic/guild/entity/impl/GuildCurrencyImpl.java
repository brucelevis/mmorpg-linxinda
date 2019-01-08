package com.wan37.logic.guild.entity.impl;

import com.wan37.logic.guild.database.GuildCurrencyDb;
import com.wan37.logic.guild.entity.GuildCurrency;

class GuildCurrencyImpl implements GuildCurrency {

    public GuildCurrencyImpl(GuildCurrencyDb guildCurrencyDb) {
        this.guildCurrencyDb = guildCurrencyDb;
    }

    @Override
    public GuildCurrencyDb getGuildCurrencyDb() {
        return guildCurrencyDb;
    }

    @Override
    public Integer getCfgId() {
        return guildCurrencyDb.getCfgId();
    }

    @Override
    public long getAmount() {
        return guildCurrencyDb.getAmount();
    }

    @Override
    public void setAmount(long amount) {
        guildCurrencyDb.setAmount(amount);
    }

    private final GuildCurrencyDb guildCurrencyDb;
}
