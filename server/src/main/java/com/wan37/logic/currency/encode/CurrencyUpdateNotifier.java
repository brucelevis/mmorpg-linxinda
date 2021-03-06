package com.wan37.logic.currency.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.VirtualItemCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 玩家虚物变化推送
 *
 * @author linda
 */
@Service
public class CurrencyUpdateNotifier {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private CurrencyItemEncoder currencyItemEncoder;

    public void notify(Player player) {
        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        String msg = encodeUpdate(currencyDb);
        if (msg == null) {
            return;
        }

        player.syncClient("虚拟物品更新：\n" + msg);

        // 虚拟物品变化标记清空
        currencyDb.getIds().clear();
    }

    private String encodeUpdate(CurrencyDb currencyDb) {
        Set<Integer> ids = currencyDb.getIds();
        if (ids.isEmpty()) {
            return null;
        }

        Map<Integer, CurrencyItemDb> items = currencyDb.getItemMap();
        return ids.stream()
                .map(i -> encodeItem(i, items.get(i)))
                .collect(Collectors.joining("\n"));
    }

    private String encodeItem(Integer id, CurrencyItemDb itemDb) {
        if (itemDb == null) {
            // 虚物用完了
            return String.format("%s：%s", configLoader.loadName(VirtualItemCfg.class, id), 0);
        }

        return currencyItemEncoder.encode(itemDb);
    }
}
