package com.wan37.logic.equipment.service.info;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EquipInfoExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private BehaviorManager behaviorManager;

    public void exec(String channelId) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            return;
        }

        EquipDb equipDb = player.getPlayerDb().getEquipDb();
        String msg = "装备栏列表：\n" + encode(equipDb);
        player.syncClient(msg);
    }

    @Deprecated
    private String encode(EquipDb equipDb) {
        return equipDb.getItems().entrySet().stream()
                .map(this::encodeEquip)
                .collect(Collectors.joining("\n"));
    }

    private String encodeEquip(Map.Entry<Integer, ItemDb> entry) {
        ItemDb itemDb = entry.getValue();
        PropsCfg propsCfg = propsCfgLoader.load(itemDb.getCfgId())
                .orElseThrow(() -> new RuntimeException("找不到对应导表"));
        String equipName = propsCfg.getName();

        Integer part = entry.getKey();
        String partName = EquipPartEnum.getName(part);

        ItemExtraEncodeBehavior behavior = (ItemExtraEncodeBehavior) behaviorManager.get(ItemExtraEncodeBehavior.class, propsCfg.getType());
        ItemExtraEncodeBehavContext ctx = new ItemExtraEncodeBehavContext(itemDb.getExtraDb());
        behavior.behave(ctx);

        String msg = "部位：%s（partId：%s），名字：%s, %s";
        return String.format(msg, partName, part, equipName, ctx.getResult());
    }
}
