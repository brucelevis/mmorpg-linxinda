package com.wan37.logic.backpack;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BackpackFacade {

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private BackpackEmptyIndexFinder backpackEmptyIndexFinder;

    public Optional<ItemDb> find(BackpackDb backpackDb, Long uid) {
        return backpackDb.getItemMap().values().stream()
                .filter(i -> Objects.equals(i.getUid(), uid))
                .findAny();
    }

    public void remove(Player player, Long uid) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = find(backpackDb, uid).orElse(null);
        if (itemDb == null) {
            return;
        }

        Integer index = itemDb.getIndex();
        backpackDb.getItemMap().remove(index);

        // 标记背包格子更新
        backpackDb.getIndexs().add(index);

        backpackUpdateNotifier.notify(player);
    }

    public void add(Player player, ItemDb itemDb) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        Integer index = backpackEmptyIndexFinder.find(backpackDb);

        itemDb.setIndex(index);
        backpackDb.getItemMap().put(index, itemDb);

        // 标记背包格子更新
        backpackDb.getIndexs().add(index);

        backpackUpdateNotifier.notify(player);
    }
}
