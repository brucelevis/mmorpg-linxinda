package com.wan37.logic.league.service.donate.item.impl;

import com.wan37.logic.league.service.donate.item.LDonateItem;
import com.wan37.logic.league.service.donate.item.ReqLDonateItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ReqLDonateItemFactory implements ReqLDonateItem.Factory {

    @Override
    public ReqLDonateItem create(String args) {
        return new ReqLDonateItemImpl(Arrays.stream(args.split(","))
                .map(this::createItem)
                .collect(Collectors.toList()));
    }

    private LDonateItem createItem(String s) {
        String[] item = s.split(":");
        Integer index = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);

        return new LDonateItemImpl(index, amount);
    }
}
