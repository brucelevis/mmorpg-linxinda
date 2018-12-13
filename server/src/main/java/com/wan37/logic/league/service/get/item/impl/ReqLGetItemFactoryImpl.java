package com.wan37.logic.league.service.get.item.impl;

import com.wan37.logic.league.service.get.item.LGetItem;
import com.wan37.logic.league.service.get.item.ReqLGetItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ReqLGetItemFactoryImpl implements ReqLGetItem.Factory {

    @Override
    public ReqLGetItem create(String args) {
        return new ReqLGetItemImpl(Arrays.stream(args.split(","))
                .map(this::createItem)
                .collect(Collectors.toList()));
    }

    private LGetItem createItem(String s) {
        String[] item = s.split(":");

        Integer index = Integer.parseInt(item[0]);
        int amount = Integer.parseInt(item[1]);
        return new LGetItemImpl(index, amount);
    }
}
