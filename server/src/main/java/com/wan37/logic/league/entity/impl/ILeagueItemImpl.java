package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueItemDb;
import com.wan37.logic.league.entity.ILeagueItem;

class ILeagueItemImpl implements ILeagueItem {

    public ILeagueItemImpl(LeagueItemDb leagueItemDb) {
        this.leagueItemDb = leagueItemDb;
    }

    @Override
    public LeagueItemDb getLItemDb() {
        return leagueItemDb;
    }

    @Override
    public Integer getIndex() {
        return leagueItemDb.getIndex_();
    }

    @Override
    public Integer getCfgId() {
        return leagueItemDb.getCfgId();
    }

    @Override
    public void setIndex(Integer index) {
        leagueItemDb.setIndex_(index);
    }

    @Override
    public int getAmount() {
        return leagueItemDb.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        leagueItemDb.setAmount(amount);
    }

    private final LeagueItemDb leagueItemDb;
}
