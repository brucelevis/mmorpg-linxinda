package com.wan37.logic.equipment.service.wear;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipWearExec {

    @Autowired
    private EquipWearer equipWearer;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    public void exec(Player player, Long itemUid) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackFacade.find(backpackDb, itemUid).orElse(null);
        if (itemDb == null) {
            player.syncClient("找不到该物品");
            return;
        }

        EquipCfg equipCfg = equipCfgLoader.load(itemDb.getCfgId()).orElse(null);
        if (equipCfg == null) {
            player.syncClient("不是装备物品，无法穿戴");
            return;
        }

        equipWearer.wear(player, itemDb, equipCfg);
    }
}
