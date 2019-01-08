package com.wan37.logic.guild.service.get.item.impl;

import com.wan37.logic.guild.service.get.item.GuildGetItem;
import com.wan37.logic.guild.service.get.item.ReqGuildGetItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class ReqGuildGetItemFactoryImpl implements ReqGuildGetItem.Factory {

    @Override
    public ReqGuildGetItem create(String args) {
        return new ReqGuildGetItemImpl(Arrays.stream(args.split(","))
                .map(this::createItem)
                .collect(Collectors.toList()));
    }

    private GuildGetItem createItem(String s) {
        String[] item = s.split(":");

        Integer index = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);
        return new GuildGetItemImpl(index, amount);
    }
}
