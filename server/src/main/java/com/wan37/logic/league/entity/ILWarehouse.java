package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueGlobalDb;

import java.util.List;

public interface ILWarehouse {

    interface Factory {

        ILWarehouse create(LeagueGlobalDb leagueGlobalDb);
    }

    void addItem(ILeagueItem leagueItem);

    int getCurSize();

    int getCapacity();

    void lock();

    void unlock();

    List<ILeagueItem> getItems();
}
