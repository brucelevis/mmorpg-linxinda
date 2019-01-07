package com.wan37.logic.shop.config;

import com.wan37.logic.props.config.VirtualItemCfg;

/**
 * 商店物品价格配置表
 *
 * @author linda
 */
public interface ShopPriceCfg {

    /**
     * 虚物id
     *
     * @return Integer
     * @see VirtualItemCfg#getId()
     */
    Integer getId();

    /**
     * 值
     *
     * @return int
     */
    int getValue();
}
