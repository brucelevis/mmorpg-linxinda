package com.wan37.logic.shop.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.shop.config.ShopCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class ShopInfoExec {

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        String head = "商店商店列表如下: \n";
        String content = configLoader.loads(ShopCfg.class).stream()
                .map(this::toTuple)
                .filter(Objects::nonNull)
                .map(this::encodeItem)
                .collect(Collectors.joining("\n"));

        player.syncClient(head + content);
    }

    private Tuple toTuple(ShopCfg shopCfg) {
        PropsCfg propsCfg = configLoader.load(PropsCfg.class, shopCfg.getItemId()).orElse(null);
        if (propsCfg == null) {
            return null;
        }

        VirtualItemCfg virtualItemCfg = configLoader.load(VirtualItemCfg.class, shopCfg.getPriceCfg().getId()).orElse(null);
        if (virtualItemCfg == null) {
            return null;
        }

        Tuple tuple = new Tuple();
        tuple.shopCfg = shopCfg;
        tuple.propsCfg = propsCfg;
        tuple.virtualItemCfg = virtualItemCfg;
        return tuple;
    }

    private String encodeItem(Tuple tuple) {
        String msg = "id：%s，name：%s，price：%s%s，desc：%s";

        ShopCfg shopCfg = tuple.shopCfg;
        PropsCfg propsCfg = tuple.propsCfg;
        VirtualItemCfg virtualItemCfg = tuple.virtualItemCfg;

        return String.format(msg, shopCfg.getId(), propsCfg.getName(), shopCfg.getPriceCfg().getValue(),
                virtualItemCfg.getName(), propsCfg.getDesc());
    }

    private static class Tuple {

        ShopCfg shopCfg;

        PropsCfg propsCfg;

        VirtualItemCfg virtualItemCfg;
    }
}
