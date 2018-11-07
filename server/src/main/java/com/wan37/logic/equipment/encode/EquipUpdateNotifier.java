package com.wan37.logic.equipment.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipUpdateNotifier {

    @Autowired
    private EquipUpdateNotifyEncoder equipUpdateNotifyEncoder;

    public void notify(Player player) {
        EquipDb equipDb = player.getPlayerDb().getEquipDb();
        GeneralResponseDto dto = equipUpdateNotifyEncoder.encode(ResultCode.EQUIP_UPDATE, equipDb);

        player.syncClient(dto);

        // 装备栏变化标记清空
        equipDb.getParts().clear();
    }
}
