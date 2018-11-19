package com.wan37.logic.equipment.service.takeoff;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.StrengthChangeEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.encode.EquipUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EquipTakeoffExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipUpdateNotifier equipUpdateNotifier;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    public void exec(Player player, Integer part) {
        EquipDb equipDb = player.getPlayerDb().getEquipDb();
        Map<Integer, ItemDb> items = equipDb.getItems();
        ItemDb equipItem = items.get(part);
        if (equipItem == null) {
            player.syncClient("无佩戴的装备部位");
            return;
        }

        //FIXME: 检查背包是否有空位

        // 脱
        items.remove(part);
        equipDb.getParts().add(part);

        // 加进背包
        backpackFacade.add(player, equipItem);

        // 打印提示
        String msg = String.format("你脱掉了%s", propsCfgLoader.getName(equipItem.getCfgId()));
        player.syncClient(msg);

        // 推送装备栏更新
        equipUpdateNotifier.notify(player);

        // 触发战力面板变化事件
        genernalEventListenersManager.fireEvent(new StrengthChangeEvent(player.getUid()));
    }
}
