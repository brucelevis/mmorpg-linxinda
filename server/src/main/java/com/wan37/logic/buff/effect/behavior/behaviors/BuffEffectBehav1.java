package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.service.addmp.PlayerMpAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢回蓝
 */
@Service
class BuffEffectBehav1 implements BuffEffectBehavior {

    @Autowired
    private PlayerMpAdder playerMpAdder;

    @Override
    public void behave(BuffEffectContext context) {
        IBuff buff = context.getBuff();
        long now = context.getNow();

        Player player = context.getPlayer();

        int addMp = Integer.parseInt(buff.getArg());
        playerMpAdder.add(player, addMp);

        buff.setLastEffectTime(now);
    }
}
