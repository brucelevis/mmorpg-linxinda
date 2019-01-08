package com.wan37.logic.guild.service.donate.item.impl;

import com.wan37.logic.guild.service.donate.item.GuildDonateItem;
import com.wan37.logic.guild.service.donate.item.ReqGuildDonateItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class ReqGuildDonateItemFactory implements ReqGuildDonateItem.Factory {

    @Override
    public ReqGuildDonateItem create(String args) {
        return new ReqGuildDonateItemImpl(Arrays.stream(args.split(","))
                .map(this::createItem)
                .collect(Collectors.toList()));
    }

    private GuildDonateItem createItem(String s) {
        String[] item = s.split(":");
        Integer index = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);

        return new GuildDonateItemImpl(index, amount);
    }
}
