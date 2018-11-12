package com.wan37.logic.props.service.use;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.equipment.service.wear.EquipWearer;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.props.behavior.use.PropsUseBehavior;
import com.wan37.logic.props.behavior.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemUseExec {

    private static final Logger LOG = Logger.getLogger(ItemUseExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private BehaviorManager behaviorManager;

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    @Autowired
    private EquipWearer equipWearer;

    public void exec(String channelId, Integer index) {
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackFacade.find(backpackDb, index).orElse(null);
        if (itemDb == null) {
            player.syncClient("找不到目标背包物品");
            return;
        }

        Integer cfgId = itemDb.getCfgId();
        PropsCfg propsCfg = propsCfgLoader.load(cfgId).orElseThrow(() -> new RuntimeException("找不到对应物品表"));
        if (!propsCfg.isCanUse()) {
            player.syncClient("不可用的物品");
            return;
        }

        //FIXME: 如果是装备
        EquipCfg equipCfg = equipCfgLoader.load(cfgId).orElse(null);
        if (equipCfg != null) {
            equipWearer.wear(player, itemDb, equipCfg);
            return;
        }

        // 背包物品-1
        backpackFacade.remove(player, index, 1);
        playerDao.save(player.getPlayerDb());

        String msg = String.format("你使用了%s", propsCfg.getName());
        player.syncClient(msg);

        // 使用
        Integer logicId = propsCfg.getUseLogicId();
        PropsUseBehavior behavior = (PropsUseBehavior) behaviorManager.get(PropsUseBehavior.class, logicId);
        behavior.behave(new PropsUseContext(player, propsCfg));
    }
}
