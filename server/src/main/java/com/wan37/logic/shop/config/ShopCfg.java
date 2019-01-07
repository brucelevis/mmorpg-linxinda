package com.wan37.logic.shop.config;

import com.wan37.logic.props.config.PropsCfg;

/**
 * 商店配置表
 *
 * @author linda
 */
public interface ShopCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 物品id
     *
     * @return Integer
     * @see PropsCfg#getId()
     */
    Integer getItemId();

    /**
     * 商店物品价格配置表
     *
     * @return ShopPriceCfg
     */
    ShopPriceCfg getPriceCfg();
}
