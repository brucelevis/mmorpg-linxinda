package com.wan37.logic.shop.config.impl;

import com.wan37.logic.shop.config.ShopPriceCfg;

/**
 * @author linda
 */
public class ShopPriceCfgImpl implements ShopPriceCfg {

    public ShopPriceCfgImpl(Integer id, int value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getValue() {
        return value;
    }

    private final Integer id;
    private final int value;
}
