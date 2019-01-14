package com.wan37.logic.props.service;

import com.wan37.behavior.BehaviorManager;
import com.wan37.config.ConfigLoader;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.service.wear.EquipWearer;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.use.PropsUseBehavior;
import com.wan37.logic.props.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class ItemUseExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private BehaviorManager behaviorManager;

    @Autowired
    private EquipWearer equipWearer;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, Integer index) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackFacade.find(backpackDb, index).orElse(null);
        if (itemDb == null) {
            player.syncClient("找不到目标背包物品");
            return;
        }

        Integer cfgId = itemDb.getCfgId();
        PropsCfg propsCfg = configLoader.load(PropsCfg.class, cfgId)
                .orElseThrow(() -> new RuntimeException("找不到对应物品表"));
        if (!propsCfg.isCanUse()) {
            player.syncClient("不可用的物品");
            return;
        }

        //FIXME: 如果是装备
        EquipCfg equipCfg = configLoader.load(EquipCfg.class, cfgId).orElse(null);
        if (equipCfg != null) {
            equipWearer.wear(player, itemDb, equipCfg);
            return;
        }

        // 背包物品-1
        backpackFacade.remove(backpackDb, index, 1);
        backpackUpdateNotifier.notify(player);

        String msg = String.format("你使用了%s", propsCfg.getName());
        player.syncClient(msg);

        // 使用
        Integer logicId = propsCfg.getUseLogicId();
        PropsUseBehavior behavior = (PropsUseBehavior) behaviorManager.get(PropsUseBehavior.class, logicId);
        behavior.behave(new PropsUseContext(player, propsCfg));
    }
}
