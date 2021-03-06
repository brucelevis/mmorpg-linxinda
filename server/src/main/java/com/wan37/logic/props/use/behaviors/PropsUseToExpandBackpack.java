package com.wan37.logic.props.use.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.use.PropsUseBehavior;
import com.wan37.logic.props.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.stereotype.Service;

/**
 * 背包扩容道具
 */
@Service
@BehaviorLogic(id = 4)
class PropsUseToExpandBackpack implements PropsUseBehavior {

    @Override
    public void behave(PropsUseContext context) {
        PropsCfg propsCfg = context.getPropsCfg();
        int amount = Integer.parseInt(propsCfg.getUseLogicArgs());

        Player player = context.getPlayer();
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        backpackDb.setCapacity(backpackDb.getCapacity() + amount);

        String msg = String.format("你扩充了%s个背包格子", amount);
        player.syncClient(msg);
    }
}
