package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.event.event.DieEvent;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.Buff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneActorEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢扣血
 *
 * @see com.wan37.logic.buff.BuffEffectEnum#DECREASE_HP
 */
@Service
@BehaviorLogic(id = 2)
class BuffEffectDecreaseHp implements BuffEffectBehavior {

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private SceneActorEncoder sceneActorEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void behave(BuffEffectContext context) {
        FightingUnit unit = context.getUnit();
        Buff buff = context.getBuff();
        long now = context.getNow();

        long oldHp = unit.getHp();
        int subHp = Integer.parseInt(buff.getArg());
        if (oldHp > subHp) {
            unit.setHp(oldHp - subHp);

            long lastEffectTime = buff.getLastEffectTime();
            if (lastEffectTime == 0) {
                // 第一次生效
                buff.setLastEffectTime(buff.getExpireTime() - buff.getContinuous());
            } else {
                buff.setLastEffectTime(lastEffectTime + buff.getInterval());
            }
        } else {
            // 死了
            generalEventListenersManager.fireEvent(new DieEvent(unit, now));
        }

        String buffTip = String.format("由于[%s]的效果，[%s]减少了%shp", buff.getName(), unit.getName(), oldHp - unit.getHp());
        String sceneActorNotify = sceneActorEncoder.encode(unit);

        AbstractScene scene = sceneActorSceneGetter.get(unit);
        scene.getPlayers().forEach(p -> p.syncClient(buffTip + "\n" + sceneActorNotify));
    }
}
