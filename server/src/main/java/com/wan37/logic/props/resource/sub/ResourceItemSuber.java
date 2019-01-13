package com.wan37.logic.props.resource.sub;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.find.BackpackExistItemFinder;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 扣除实物
 *
 * @author linda
 */
@Service
public class ResourceItemSuber {

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private BackpackExistItemFinder backpackExistItemFinder;

    public boolean sub(ResourceElement element, Player player) {
        PropsCfg propsCfg = propsCfgLoader.load(element.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return false;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        List<ItemDb> existItems = backpackExistItemFinder.find(backpackDb, propsCfg.getId());
        long existAmount = existItems.stream()
                .mapToLong(ItemDb::getAmount)
                .sum();

        long needAmount = element.getAmount();
        if (existAmount < needAmount) {
            return false;
        }

        for (int i = 0; i < existItems.size() && needAmount > 0; i++) {
            ItemDb itemDb = existItems.get(i);
            int curAmount = itemDb.getAmount();
            if (curAmount >= needAmount) {
                // 足够扣
                itemDb.setAmount(curAmount - (int) needAmount);
                needAmount = 0;
            } else {
                itemDb.setAmount(0);
                needAmount -= curAmount;
            }

            Integer index = itemDb.getIndex();
            if (itemDb.getAmount() == 0) {
                // 清空格子
                backpackDb.getItemMap().remove(index);
            }

            // 标记推送的格子索引
            backpackDb.getIndex().add(index);
        }

        return true;
    }
}
