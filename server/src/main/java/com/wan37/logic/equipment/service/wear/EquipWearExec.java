package com.wan37.logic.equipment.service.wear;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipWearExec {

    private static final Logger LOG = Logger.getLogger(EquipWearExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private EquipWearer equipWearer;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    public void exec(String channelId, Long itemUid) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

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
