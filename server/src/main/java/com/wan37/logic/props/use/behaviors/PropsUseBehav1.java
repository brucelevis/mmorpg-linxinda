package com.wan37.logic.props.use.behaviors;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.service.FightingUnitMpAdder;
import com.wan37.logic.props.use.PropsUseBehavior;
import com.wan37.logic.props.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一次性回复蓝量
 */
@Service
class PropsUseBehav1 implements PropsUseBehavior {

    @Autowired
    private FightingUnitMpAdder fightingUnitMpAdder;

    @Override
    public void behave(PropsUseContext context) {
        Player player = context.getPlayer();
        PropsCfg propsCfg = context.getPropsCfg();

        long curMp = player.getMp();
        int addMp = Integer.parseInt(propsCfg.getUseLogicArgs());
        fightingUnitMpAdder.add(player, addMp);

        long result = player.getMp();
        if (result != curMp) {
            String msg = String.format("你恢复了%smp", result - curMp);
            player.syncClient(msg);
        }

        // TODO: 推送玩家状态变化给场景其他玩家
    }
}
