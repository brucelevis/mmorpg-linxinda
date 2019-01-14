package com.wan37.logic.shop.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.shop.config.ShopCfg;
import com.wan37.logic.shop.config.ShopPriceCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class ShopBuyExec {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    public void exec(Player player, Integer id, int amount) {
        ShopCfg shopCfg = configLoader.load(ShopCfg.class, id).orElse(null);
        if (shopCfg == null) {
            player.syncClient("不存在的商品");
            return;
        }

        PropsCfg propsCfg = configLoader.load(PropsCfg.class, shopCfg.getItemId()).orElse(null);
        if (propsCfg == null) {
            return;
        }

        if (propsCfg.getMaxOverLay() <= 1 && amount > 1) {
            player.syncClient("不可堆叠物品一次只能买一个");
            return;
        }

        // 判断钱够不够
        ShopPriceCfg priceCfg = shopCfg.getPriceCfg();
        long cost = amount * priceCfg.getValue();
        long cur = resourceFacade.queryCurrency(priceCfg.getId(), player);
        if (cur < cost) {
            player.syncClient("钱不足");
            return;
        }

        ResourceElement give = createResElement(propsCfg.getId(), amount);
        boolean success = resourceFacade.giveResource(give, player);
        if (success) {
            ResourceElement consume = createResElement(priceCfg.getId(), cost);
            resourceFacade.consumeResource(consume, player);
        }

        // 背包更新推送
        backpackUpdateNotifier.notify(player);

        // 虚拟物品更新推送
        currencyUpdateNotifier.notify(player);
    }

    private ResourceElement createResElement(Integer id, long amount) {
        return new ResourceElementImpl(id, amount);
    }
}
