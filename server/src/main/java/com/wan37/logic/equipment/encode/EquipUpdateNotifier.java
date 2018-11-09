package com.wan37.logic.equipment.encode;

import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class EquipUpdateNotifier {

    public void notify(Player player) {
        EquipDb equipDb = player.getPlayerDb().getEquipDb();

        //FIXME： 装备栏更新通知
        player.syncClient("装备栏更新通知");

        // 装备栏变化标记清空
        equipDb.getParts().clear();
    }
}
