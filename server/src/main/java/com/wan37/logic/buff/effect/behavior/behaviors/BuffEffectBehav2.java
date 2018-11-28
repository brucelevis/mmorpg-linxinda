package com.wan37.logic.buff.effect.behavior.behaviors;

import com.wan37.event.DieEvent;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.behavior.BuffEffectBehavior;
import com.wan37.logic.buff.effect.behavior.BuffEffectContext;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.scene.SceneGlobalManager;
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
    private MonsterEncoder monsterEncoder;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    /**
     * FIXME: 玩家buff中毒扣血没提示
     */
    @Override
    public void behave(BuffEffectContext context) {
        FightingUnit unit = context.getUnit();
        IBuff buff = context.getBuff();
        long now = context.getNow();

        long curHp = unit.getHp();
        int subHp = Integer.parseInt(buff.getArg());
        if (curHp > subHp) {
            unit.setHp(curHp - subHp);

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

        //FIXME: 如果是怪物
        if (unit instanceof Monster) {
            Monster monster = (Monster) unit;

            // 通知场景玩家怪物状态更新
            String monsterUpdate = "怪物状态更新推送|" + monsterEncoder.encode(monster);
            sceneGlobalManager.getScene(monster.getSceneId()).getPlayers()
                    .forEach(p -> p.syncClient(monsterUpdate));
        }
    }
}
