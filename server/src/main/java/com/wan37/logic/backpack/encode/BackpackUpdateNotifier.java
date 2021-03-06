package com.wan37.logic.backpack.encode;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 背包更新推送
 *
 * @author linda
 */
@Service
public class BackpackUpdateNotifier {

    @Autowired
    private BackpackItemSimpleInfoEncoder backpackItemSimpleInfoEncoder;

    public void notify(Player player) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        String msg = encodeUpdate(backpackDb);
        if (msg == null) {
            return;
        }

        // 格子有更新推送
        player.syncClient("背包物品更新：\n" + msg);

        // 背包格子变化标记清空
        backpackDb.getIndex().clear();
    }

    private String encodeUpdate(BackpackDb backpackDb) {
        Set<Integer> index = backpackDb.getIndex();
        if (index.isEmpty()) {
            return null;
        }

        Map<Integer, ItemDb> items = backpackDb.getItemMap();
        return index.stream()
                .map(i -> encodeItem(i, items.get(i)))
                .collect(Collectors.joining("\n"));
    }

    private String encodeItem(Integer index, ItemDb itemDb) {
        if (itemDb == null) {
            // 背包物品没格子
            return String.format("格子：%s，数量：%s ", index, 0);
        }

        return backpackItemSimpleInfoEncoder.encode(itemDb);
    }
}
