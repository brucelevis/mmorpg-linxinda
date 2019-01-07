package com.wan37.logic.equipment.service;

import com.google.common.collect.ImmutableList;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.StrengthChangeEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.encode.EquipUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author linda
 */
@Service
public class EquipTakeoffExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipUpdateNotifier equipUpdateNotifier;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private BackpackEmptyIndexFinder backpackEmptyIndexFinder;

    public void exec(Player player, Integer part) {
        PlayerDb playerDb = player.getPlayerDb();
        EquipDb equipDb = player.getPlayerDb().getEquipDb();
        Map<Integer, ItemDb> items = equipDb.getItems();
        ItemDb equipItem = items.get(part);
        if (equipItem == null) {
            player.syncClient("无佩戴的装备部位");
            return;
        }

        // 检查背包是否有空位
        int index = backpackEmptyIndexFinder.find(playerDb.getBackpackDb());
        if (index < 0) {
            player.syncClient("没有多余空位");
            return;
        }

        // 脱
        items.remove(part);
        equipDb.getParts().add(part);

        // 加进背包
        backpackFacade.add(player, ImmutableList.of(equipItem));

        // 打印提示
        String msg = String.format("你脱掉了%s", propsCfgLoader.getName(equipItem.getCfgId()));
        player.syncClient(msg);

        // 推送装备栏更新
        equipUpdateNotifier.notify(player);

        // 触发战力面板变化事件
        generalEventListenersManager.fireEvent(new StrengthChangeEvent(player.getUid()));
    }
}
