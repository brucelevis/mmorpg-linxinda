package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeagueCurrency;
import com.wan37.logic.league.entity.ILeagueItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

class ILWarehouseImpl implements ILWarehouse {

    public ILWarehouseImpl(LeagueGlobalDb leagueGlobalDb, Map<Integer, ILeagueItem> items, Map<Integer, ILeagueCurrency> currency, Lock lock) {
        this.leagueGlobalDb = leagueGlobalDb;
        this.items = items;
        this.currency = currency;
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
    public void rmItem(Integer index, int amount) {
        ILeagueItem leagueItem = items.get(index);
        if (leagueItem.getAmount() == amount) {
            // 注意：这里有个坑，不能用remove，否则Jpa不会联级删除many端的记录
            leagueGlobalDb.setItems(leagueGlobalDb.getItems().stream()
                    .filter(i -> !Objects.equals(i.getIndex_(), index))
                    .collect(Collectors.toSet()));
            items.remove(index);
            return;
        }

        leagueItem.setAmount(leagueItem.getAmount() - amount);
    }

    @Override
    public void addCurrency(ILeagueCurrency leagueCurrency) {
        ILeagueCurrency target = currency.get(leagueCurrency.getCfgId());
        if (target == null) {
            currency.put(leagueCurrency.getCfgId(), leagueCurrency);
            leagueGlobalDb.getCurrency().add(leagueCurrency.getLCurrencyDb());
            return;
        }

        //FIXME: 上限问题
        target.setAmount(target.getAmount() + leagueCurrency.getAmount());
    }

    @Override
    public void rmCurrency(Integer cfgId, long amount) {
        ILeagueCurrency leagueCurrency = currency.get(cfgId);
        if (leagueCurrency.getAmount() == amount) {
            // 注意：这里有个坑，不能用remove，否则Jpa不会联级删除many端的记录
            leagueGlobalDb.setCurrency(leagueGlobalDb.getCurrency().stream()
                    .filter(c -> !Objects.equals(c.getCfgId(), cfgId))
                    .collect(Collectors.toSet()));
            currency.remove(cfgId);
            return;
        }

        leagueCurrency.setAmount(leagueCurrency.getAmount() - amount);
    }

    @Override
    public int queryItem(Integer index) {
        ILeagueItem leagueItem = items.get(index);
        if (leagueItem == null) {
            return 0;
        }
        return leagueItem.getAmount();
    }

    @Override
    public ILeagueItem getItem(Integer index) {
        return items.get(index);
    }

    @Override
    public long queryCurrency(Integer cfgId) {
        ILeagueCurrency leagueCurrency = currency.get(cfgId);
        if (leagueCurrency == null) {
            return 0;
        }

        return leagueCurrency.getAmount();
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

    @Override
    public List<ILeagueCurrency> getCurrency() {
        return new ArrayList<>(currency.values());
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
    private final Map<Integer, ILeagueCurrency> currency;
    private final Lock lock;
}
