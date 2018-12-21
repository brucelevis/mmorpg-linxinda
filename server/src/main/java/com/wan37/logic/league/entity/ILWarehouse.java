package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueGlobalDb;

import java.util.List;

public interface ILWarehouse {

    interface Factory {

        ILWarehouse create(LeagueGlobalDb leagueGlobalDb);
    }

    void addItem(ILeagueItem leagueItem);

    void rmItem(Integer index, int amount);

    void addCurrency(ILeagueCurrency leagueCurrency);

    void rmCurrency(Integer cfgId, long amount);

    int queryItem(Integer index);

    ILeagueItem getItem(Integer index);

    long queryCurrency(Integer cfgId);

    int getCurSize();

    int getCapacity();

    void lock();

    void unlock();

    List<ILeagueItem> getItems();

    List<ILeagueCurrency> getCurrency();
}