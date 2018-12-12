package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueItemDb;

public interface ILeagueItem {

    interface Factory {

        ILeagueItem create(LeagueItemDb itemDb);
    }

    LeagueItemDb getLItemDb();

    Integer getIndex();

    Integer getCfgId();

    void setIndex(Integer index);

    int getAmount();
}
