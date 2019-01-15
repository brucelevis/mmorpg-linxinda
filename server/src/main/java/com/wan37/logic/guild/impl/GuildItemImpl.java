package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.GuildItem;

class GuildItemImpl implements GuildItem {

    public GuildItemImpl(GuildItemDb guildItemDb) {
        this.guildItemDb = guildItemDb;
    }

    @Override
    public GuildItemDb getGuildItemDb() {
        return guildItemDb;
    }

    @Override
    public Integer getIndex() {
        return guildItemDb.getIndex_();
    }

    @Override
    public Integer getCfgId() {
        return guildItemDb.getCfgId();
    }

    @Override
    public void setIndex(Integer index) {
        guildItemDb.setIndex_(index);
    }

    @Override
    public int getAmount() {
        return guildItemDb.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        guildItemDb.setAmount(amount);
    }

    private final GuildItemDb guildItemDb;
}
