package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.shop.config.ShopCfg;
import com.wan37.logic.shop.config.impl.ShopCfgImpl;

/**
 * 商店配置表实体类
 *
 * @author linda
 */
public class ShopCfgExcel implements ConfigFactory<ShopCfg> {

    private Integer id;
    private Integer itemId;
    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public ShopCfg create() {
        return new ShopCfgImpl(this);
    }
}
