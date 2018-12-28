package com.wan37.logic.shop.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.shop.config.ShopCfg;
import com.wan37.logic.shop.config.ShopCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShopInfoExec {

    @Autowired
    private ShopCfgLoader shopCfgLoader;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    public void exec(Player player) {
        String head = "商店商店列表如下: \n";
        String content = shopCfgLoader.loads().stream()
                .map(this::toTruple)
                .filter(Objects::nonNull)
                .map(this::encodeItem)
                .collect(Collectors.joining("\n"));

        player.syncClient(head + content);
    }

    private Truple toTruple(ShopCfg shopCfg) {
        PropsCfg propsCfg = propsCfgLoader.load(shopCfg.getItemId()).orElse(null);
        if (propsCfg == null) {
            return null;
        }

        VirtualItemCfg virtualItemCfg = virtualItemCfgLoader.load(shopCfg.getPriceCfg().getId()).orElse(null);
        if (virtualItemCfg == null) {
            return null;
        }

        Truple truple = new Truple();
        truple.shopCfg = shopCfg;
        truple.propsCfg = propsCfg;
        truple.virtualItemCfg = virtualItemCfg;
        return truple;
    }

    private String encodeItem(Truple truple) {
        String msg = "id：%s，name：%s，price：%s%s，desc：%s";

        ShopCfg shopCfg = truple.shopCfg;
        PropsCfg propsCfg = truple.propsCfg;
        VirtualItemCfg virtualItemCfg = truple.virtualItemCfg;

        return String.format(msg, shopCfg.getId(), propsCfg.getName(), shopCfg.getPriceCfg().getValue(),
                virtualItemCfg.getName(), propsCfg.getDesc());
    }

    private static class Truple {

        ShopCfg shopCfg;

        PropsCfg propsCfg;

        VirtualItemCfg virtualItemCfg;
    }
}
