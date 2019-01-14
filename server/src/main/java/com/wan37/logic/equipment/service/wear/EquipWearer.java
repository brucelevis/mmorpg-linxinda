package com.wan37.logic.equipment.service.wear;

import com.google.common.collect.ImmutableList;
import com.wan37.config.ConfigLoader;
import com.wan37.event.entity.EquipWearEvent;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.StrengthChangeEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.encode.EquipUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 装备穿戴逻辑
 *
 * @author linda
 */
@Service
public class EquipWearer {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private EquipUpdateNotifier equipUpdateNotifier;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private ConfigLoader configLoader;

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
            backpackFacade.add(player, ImmutableList.of(old));
        }

        // 打印提示
        String msg = String.format("你穿上了%s", configLoader.loadName(PropsCfg.class, equipCfg.getId()));
        player.syncClient(msg);

        // 推送装备栏更新
        equipUpdateNotifier.notify(player);

        // 触发战力面板变化事件
        //FIXME: event写错了
        generalEventListenersManager.fireEvent(new StrengthChangeEvent(player.getUid()));

        generalEventListenersManager.fireEvent(new EquipWearEvent(player));
    }
}
