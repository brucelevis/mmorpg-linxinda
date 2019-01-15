package com.wan37.logic.buff.effect;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.Buff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Buff效果处理类
 *
 * @author linda
 */
@Service
public class BuffEffectHandler {

    @Autowired
    private BehaviorManager behaviorManager;

    public void handle(FightingUnit unit, Buff buff, long now) {
        Integer effectId = buff.getEffectId();

        BuffEffectBehavior behavior = (BuffEffectBehavior) behaviorManager.get(BuffEffectBehavior.class, effectId);
        behavior.behave(new BuffEffectContext(unit, buff, now));
    }
}
