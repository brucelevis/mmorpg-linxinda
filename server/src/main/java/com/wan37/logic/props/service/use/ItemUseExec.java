package com.wan37.logic.props.service.use;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.props.behavior.PropsUseBehavior;
import com.wan37.logic.props.behavior.PropsUseContext;
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

        // 背包物品-1
        backpackFacade.remove(player, index, 1);

        playerDao.save(player.getPlayerDb());

        // 使用
        Integer logicId = propsCfg.getUseLogicId();
        PropsUseBehavior behavior = (PropsUseBehavior) behaviorManager.get(PropsUseBehavior.class, logicId);
        behavior.behave(new PropsUseContext(player, propsCfg));
    }
}
