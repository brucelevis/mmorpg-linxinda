package com.wan37.logic.equipment.service.takeoff;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.StrengthChangeEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.encode.EquipUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EquipTakeoffExec {

    private static final Logger LOG = Logger.getLogger(EquipTakeoffExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private EquipUpdateNotifier equipUpdateNotifier;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void exec(String channelId, Integer part) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

        EquipDb equipDb = player.getPlayerDb().getEquipDb();
        Map<Integer, ItemDb> items = equipDb.getItems();
        ItemDb equipItem = items.get(part);
        if (equipItem == null) {
            player.syncClient("无佩戴的装备部位");
            return;
        }

        // 脱
        items.remove(part);
        equipDb.getParts().add(part);

        // 加进背包
        backpackFacade.add(player, equipItem);

        playerDao.save(player.getPlayerDb());

        // 推送装备栏更新
        equipUpdateNotifier.notify(player);

        // 触发战力面板变化事件
        genernalEventListenersManager.fireEvent(new StrengthChangeEvent(player.getUid()));
    }
}
