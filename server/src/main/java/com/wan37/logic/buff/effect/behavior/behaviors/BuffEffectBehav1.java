package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.player.service.addmp.FightingUnitMpAdder;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.SceneActorEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢回蓝
 */
@Service
class BuffEffectBehav1 implements BuffEffectBehavior {

    @Autowired
    private FightingUnitMpAdder fightingUnitMpAdder;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SceneActorEncoder sceneActorEncoder;

    @Override
    public void behave(BuffEffectContext context) {
        IBuff buff = context.getBuff();

        FightingUnit unit = context.getUnit();
        long curMp = unit.getMp();

        int addMp = Integer.parseInt(buff.getArg());
        fightingUnitMpAdder.add(unit, addMp);

        long result = unit.getMp();
        if (result != curMp) {
            String buffTip = String.format("由于[%s]的效果，[%s]恢复了%smp", buff.getName(), unit.getName(), result - curMp);
            String sceneActorNotify = sceneActorEncoder.encode(unit);

            Scene scene = sceneGlobalManager.getScene(unit.getSceneId());
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
