package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeagueCurrency;
import com.wan37.logic.league.entity.ILeagueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ILWarehouseFactory implements ILWarehouse.Factory {

    @Autowired
    private ILeagueItem.Factory itemFactory;

    @Autowired
    private ILeagueCurrency.Factory currencyFactory;

    @Override
    public ILWarehouse create(LeagueGlobalDb leagueGlobalDb) {
        Lock lock = new ReentrantLock();
        Map<Integer, ILeagueItem> items = leagueGlobalDb.getItems().stream()
                .map(i -> itemFactory.create(i))
                .collect(Collectors.toMap(ILeagueItem::getIndex, Function.identity()));

        Map<Integer, ILeagueCurrency> currency = leagueGlobalDb.getCurrency().stream()
                .map(c -> currencyFactory.create(c))
                .collect(Collectors.toMap(ILeagueCurrency::getCfgId, Function.identity()));

        return new ILWarehouseImpl(leagueGlobalDb, items, currency, lock);
    }
}
