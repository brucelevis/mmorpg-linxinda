package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.event.DieEvent;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.SceneActorEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢扣血
 */
@Service
class BuffEffectBehav2 implements BuffEffectBehavior {

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SceneActorEncoder sceneActorEncoder;

    @Override
    public void behave(BuffEffectContext context) {
        FightingUnit unit = context.getUnit();
        IBuff buff = context.getBuff();
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
            genernalEventListenersManager.fireEvent(new DieEvent(unit, now));
        }

        String buffTip = String.format("由于[%s]的效果，[%s]减少了%shp", buff.getName(), unit.getName(), oldHp - unit.getHp());
        String sceneActorNotify = sceneActorEncoder.encode(unit);

        Scene scene = sceneGlobalManager.getScene(unit.getSceneId());
        scene.getPlayers().forEach(p -> p.syncClient(buffTip + "\n" + sceneActorNotify));
    }
}
