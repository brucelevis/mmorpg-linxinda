package com.wan37.logic.props.behavior.use.behaviors;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.service.addmp.PlayerMpAdder;
import com.wan37.logic.props.behavior.use.PropsUseBehavior;
import com.wan37.logic.props.behavior.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一次性回复蓝量
 */
@Service
class PropsUseBehav1 implements PropsUseBehavior {

    @Autowired
    private PlayerMpAdder playerMpAdder;

    @Override
    public void behave(PropsUseContext context) {
        Player player = context.getPlayer();
        PropsCfg propsCfg = context.getPropsCfg();

        int addMp = Integer.parseInt(propsCfg.getUseLogicArgs());
        playerMpAdder.add(player, addMp);

        // TODO: 推送玩家状态变化给场景其他玩家
    }
}
