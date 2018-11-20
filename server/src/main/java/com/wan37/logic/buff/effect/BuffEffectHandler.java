package com.wan37.logic.buff.effect;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuffEffectHandler {

    @Autowired
    private BehaviorManager behaviorManager;

    public void handle(Player player, Monster monster, IBuff buff, long now) {
        Integer effectId = buff.getEffectId();

        BuffEffectBehavior behavior = (BuffEffectBehavior) behaviorManager.get(BuffEffectBehavior.class, effectId);
        behavior.behave(new BuffEffectContext(player, monster, buff, now));
    }
}