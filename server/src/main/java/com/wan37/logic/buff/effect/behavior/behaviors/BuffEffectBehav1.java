package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.player.service.addmp.PlayerMpAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓慢回蓝（人才有mp）
 */
@Service
class BuffEffectBehav1 implements BuffEffectBehavior {

    @Autowired
    private PlayerMpAdder playerMpAdder;

    @Override
    public void behave(BuffEffectContext context) {
        IBuff buff = context.getBuff();

        FightingUnit unit = context.getUnit();
        long curMp = unit.getMp();

        int addMp = Integer.parseInt(buff.getArg());
        playerMpAdder.add(unit, addMp);

        long result = unit.getMp();
        if (result != curMp) {
            String msg = String.format("由于%s的效果，你恢复了%smp", buff.getName(), result - curMp);
            unit.getPlayer().syncClient(msg);
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
