package com.wan37.logic.shop.config.impl;

import com.wan37.config.excel.ShopCfgExcel;
import com.wan37.logic.shop.config.ShopCfg;
import com.wan37.logic.shop.config.ShopPriceCfg;

/**
 * @author linda
 */
public class ShopCfgImpl implements ShopCfg {

    public ShopCfgImpl(ShopCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;

        shopPriceCfg = initShopPrice();
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
    }

    @Override
    public Integer getItemId() {
        return cfgExcel.getItemId();
    }

    @Override
    public ShopPriceCfg getPriceCfg() {
        return shopPriceCfg;
    }

    private ShopPriceCfg initShopPrice() {
        String[] s = cfgExcel.getPrice().split(":");
        Integer id = Integer.parseInt(s[0]);
        int value = Integer.parseInt(s[1]);

        return new ShopPriceCfgImpl(id, value);
    }

    private final ShopCfgExcel cfgExcel;
    private ShopPriceCfg shopPriceCfg;
}
