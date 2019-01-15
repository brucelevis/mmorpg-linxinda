package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildGlobalDb;
import com.wan37.logic.guild.GuildCurrency;
import com.wan37.logic.guild.GuildItem;
import com.wan37.logic.guild.GuildWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class GuildWarehouseImpl implements GuildWarehouse {

    public GuildWarehouseImpl(GuildGlobalDb guildGlobalDb, Map<Integer, GuildItem> items, Map<Integer, GuildCurrency> currency) {
        this.guildGlobalDb = guildGlobalDb;
        this.items = items;
        this.currency = currency;
    }

    @Override
    public void addItem(GuildItem guildItem) {
        Integer index = findEmptyIndex();
        if (index == -1) {
            return;
        }

        guildItem.setIndex(index);
        guildGlobalDb.getItems().add(guildItem.getGuildItemDb());
        items.put(guildItem.getIndex(), guildItem);
    }

    @Override
    public void rmItem(Integer index, int amount) {
        GuildItem leagueItem = items.get(index);
        if (leagueItem.getAmount() == amount) {
            // 注意：这里有个坑，不能用remove，否则Jpa不会联级删除many端的记录
            guildGlobalDb.setItems(guildGlobalDb.getItems().stream()
                    .filter(i -> !Objects.equals(i.getIndex_(), index))
                    .collect(Collectors.toSet()));
            items.remove(index);
            return;
        }

        leagueItem.setAmount(leagueItem.getAmount() - amount);
    }

    @Override
    public void addCurrency(GuildCurrency guildCurrency) {
        GuildCurrency target = currency.get(guildCurrency.getCfgId());
        if (target == null) {
            currency.put(guildCurrency.getCfgId(), guildCurrency);
            guildGlobalDb.getCurrency().add(guildCurrency.getGuildCurrencyDb());
            return;
        }

        //FIXME: 上限问题
        target.setAmount(target.getAmount() + guildCurrency.getAmount());
    }

    @Override
    public void rmCurrency(Integer cfgId, long amount) {
        GuildCurrency leagueCurrency = currency.get(cfgId);
        if (leagueCurrency.getAmount() == amount) {
            // 注意：这里有个坑，不能用remove，否则Jpa不会联级删除many端的记录
            guildGlobalDb.setCurrency(guildGlobalDb.getCurrency().stream()
                    .filter(c -> !Objects.equals(c.getCfgId(), cfgId))
                    .collect(Collectors.toSet()));
            currency.remove(cfgId);
            return;
        }

        leagueCurrency.setAmount(leagueCurrency.getAmount() - amount);
    }

    @Override
    public int queryItem(Integer index) {
        GuildItem leagueItem = items.get(index);
        if (leagueItem == null) {
            return 0;
        }
        return leagueItem.getAmount();
    }

    @Override
    public GuildItem getItem(Integer index) {
        return items.get(index);
    }

    @Override
    public long queryCurrency(Integer cfgId) {
        GuildCurrency leagueCurrency = currency.get(cfgId);
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
        return guildGlobalDb.getCapacity();
    }

    @Override
    public List<GuildItem> getItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<GuildCurrency> getCurrency() {
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

    private final GuildGlobalDb guildGlobalDb;
    private final Map<Integer, GuildItem> items;
    private final Map<Integer, GuildCurrency> currency;
}
