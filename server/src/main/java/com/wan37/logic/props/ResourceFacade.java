package com.wan37.logic.props;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.add.ResourceAdder;
import com.wan37.logic.props.resource.sub.ResourceSuber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFacade {

    @Autowired
    private ResourceAdder resourceAdder;

    @Autowired
    private ResourceSuber resourceSuber;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    @Autowired
    private BackpackFacade backpackFacade;

    public void giveResource(ResourceCollection res, Player player) {
        int needCapacity = (int) res.getElements().stream()
                .filter(e -> e.getCfgId() >= 200)
                .count();
        if (backpackFacade.getSpareCapacity(player) < needCapacity) {
            player.syncClient("背包空间不足，请先整理");
            return;
        }

        //TODO: 检查货币是否超上限

        res.getElements().forEach(e -> giveResource(e, player));

        // 背包更新推送
        backpackUpdateNotifier.notify(player);

        // 虚拟物品更新推送
        currencyUpdateNotifier.notify(player);
    }

    public boolean giveResource(ResourceElement element, Player player) {
        return resourceAdder.add(element, player);
    }

    public boolean consumeResource(ResourceElement element, Player player) {
        return resourceSuber.sub(element, player);
    }

    public long queryCurrency(Integer id, Player player) {
        CurrencyItemDb itemDb = player.getPlayerDb().getCurrencyDb().getItemMap().get(id);
        return itemDb == null ? 0 : itemDb.getAmount();
    }
}
