package com.wan37.logic.props;

import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.add.ResourceAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFacade {

    @Autowired
    private ResourceAdder resourceAdder;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    public void giveResource(ResourceCollection res, Player player) {
        res.getElements().forEach(e -> giveResource(e, player));

        // 背包更新推送
        backpackUpdateNotifier.notify(player);

        // 虚拟物品更新推送
        currencyUpdateNotifier.notify(player);
    }

    public boolean giveResource(ResourceElement element, Player player) {
        return resourceAdder.add(element, player);
    }
}
