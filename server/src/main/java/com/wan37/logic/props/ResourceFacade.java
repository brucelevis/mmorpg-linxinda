package com.wan37.logic.props;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifyEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.add.ResourceAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceFacade {

    @Autowired
    private ResourceAdder resourceAdder;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private CurrencyUpdateNotifyEncoder currencyUpdateNotifyEncoder;

    public void giveResource(ResourceCollection res, Player player) {
        res.getElements().forEach(e -> resourceAdder.add(e, player));
        playerDao.save(player.getPlayerDb());

        // 背包更新推送
        backpackUpdateNotifier.notify(player);

        // 虚拟物品更新推送
        currencyUpdateNotify(player);
    }

    private void currencyUpdateNotify(Player player) {
        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        GeneralResponseDto dto = currencyUpdateNotifyEncoder.encode(ResultCode.CURRENCY_UPDATE, currencyDb);
        if (dto == null) {
            return;
        }

        player.syncClient(dto);

        // 虚拟物品变化标记清空
        currencyDb.getIds().clear();
    }
}
