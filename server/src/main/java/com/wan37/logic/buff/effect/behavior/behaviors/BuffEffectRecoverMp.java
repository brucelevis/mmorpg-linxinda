package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.Buff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.player.service.FightingUnitMpAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneActorEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢回蓝
 *
 * @see com.wan37.logic.buff.BuffEffectEnum#RECOVER_MP
 */
@Service
@BehaviorLogic(id = 1)
class BuffEffectRecoverMp implements BuffEffectBehavior {

    @Autowired
    private FightingUnitMpAdder fightingUnitMpAdder;

    @Autowired
    private SceneActorEncoder sceneActorEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void behave(BuffEffectContext context) {
        Buff buff = context.getBuff();

        FightingUnit unit = context.getUnit();
        long curMp = unit.getMp();

        int addMp = Integer.parseInt(buff.getArg());
        fightingUnitMpAdder.add(unit, addMp);

        long result = unit.getMp();
        if (result != curMp) {
            String buffTip = String.format("由于[%s]的效果，[%s]恢复了%smp", buff.getName(), unit.getName(), result - curMp);
            String sceneActorNotify = sceneActorEncoder.encode(unit);

            AbstractScene scene = sceneActorSceneGetter.get(unit);
            scene.getPlayers().forEach(p -> p.syncClient(buffTip + "\n" + sceneActorNotify));
        }

        // 持续类作用次数，上次生效时间 = 上次生效时间 + 每次间隔
        long lastEffectTime = buff.getLastEffectTime();
        if (lastEffectTime == 0) {
            // 第一次生效
            buff.setLastEffectTime(buff.getExpireTime() - buff.getContinuous());
            return;
        }

        buff.setLastEffectTime(lastEffectTime + buff.getInterval());
    }
}
