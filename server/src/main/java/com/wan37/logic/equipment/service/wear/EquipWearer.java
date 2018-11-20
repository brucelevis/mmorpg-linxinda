package com.wan37.logic.equipment.service.wear;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.StrengthChangeEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.encode.EquipUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipWearer {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipUpdateNotifier equipUpdateNotifier;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    public void wear(Player player, ItemDb itemDb, EquipCfg equipCfg) {
        //TODO: 检查穿戴条件

        Integer part = equipCfg.getPart();
        EquipDb equipDb = player.getPlayerDb().getEquipDb();

        // 取得装备栏该部位的装备
        ItemDb old = equipDb.getItems().get(part);

        // 穿
        equipDb.getItems().put(part, itemDb);
        equipDb.getParts().add(part);

        // 背包移除
        backpackFacade.remove(player, itemDb.getUid());

        if (old != null) {
            // 旧装备放进背包
            backpackFacade.add(player, old);
        }

        // 打印提示
        String msg = String.format("你穿上了%s", propsCfgLoader.getName(equipCfg.getId()));
        player.syncClient(msg);

        // 推送装备栏更新
        equipUpdateNotifier.notify(player);

        // 触发战力面板变化事件
        genernalEventListenersManager.fireEvent(new StrengthChangeEvent(player.getUid()));
    }
}
