package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILeagueItem;
import com.wan37.logic.league.entity.ILWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

class ILWarehouseImpl implements ILWarehouse {

    public ILWarehouseImpl(LeagueGlobalDb leagueGlobalDb, Map<Integer, ILeagueItem> items, Lock lock) {
        this.leagueGlobalDb = leagueGlobalDb;
        this.items = items;
        this.lock = lock;
    }

    @Override
    public void addItem(ILeagueItem leagueItem) {
        Integer index = findEmptyIndex();
        if (index == -1) {
            return;
        }

        leagueItem.setIndex(index);
        leagueGlobalDb.getItems().add(leagueItem.getLItemDb());
        items.put(leagueItem.getIndex(), leagueItem);
    }

    @Override
    public int getCurSize() {
        return items.size();
    }

    @Override
    public int getCapacity() {
        return leagueGlobalDb.getCapacity();
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }

    @Override
    public List<ILeagueItem> getItems() {
        return new ArrayList<>(items.values());
    }

    private Integer findEmptyIndex() {
        for (int i = 1; i <= getCapacity(); i++) {
            if (!items.containsKey(i)) {
                return i;
            }
        }

        return -1;
    }

    private final LeagueGlobalDb leagueGlobalDb;
    private final Map<Integer, ILeagueItem> items;
    private final Lock lock;
}
