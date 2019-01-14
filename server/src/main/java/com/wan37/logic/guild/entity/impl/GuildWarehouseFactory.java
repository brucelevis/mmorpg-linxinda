package com.wan37.logic.guild.entity.impl;

import com.wan37.logic.guild.database.GuildGlobalDb;
import com.wan37.logic.guild.entity.GuildCurrency;
import com.wan37.logic.guild.entity.GuildItem;
import com.wan37.logic.guild.entity.GuildWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class GuildWarehouseFactory implements GuildWarehouse.Factory {

    @Autowired
    private GuildItem.Factory itemFactory;

    @Autowired
    private GuildCurrency.Factory currencyFactory;

    @Override
    public GuildWarehouse create(GuildGlobalDb guildGlobalDb) {
        Map<Integer, GuildItem> items = guildGlobalDb.getItems().stream()
                .map(i -> itemFactory.create(i))
                .collect(Collectors.toMap(GuildItem::getIndex, Function.identity()));

        Map<Integer, GuildCurrency> currency = guildGlobalDb.getCurrency().stream()
                .map(c -> currencyFactory.create(c))
                .collect(Collectors.toMap(GuildCurrency::getCfgId, Function.identity()));

        return new GuildWarehouseImpl(guildGlobalDb, items, currency);
    }
}
