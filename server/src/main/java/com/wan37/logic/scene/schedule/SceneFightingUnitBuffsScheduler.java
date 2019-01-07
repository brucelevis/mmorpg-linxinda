package com.wan37.logic.scene.schedule;

import com.wan37.logic.buff.effect.BuffEffectHandler;
import com.wan37.logic.buff.entity.Buff;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SceneFightingUnitBuffsScheduler {

    @Autowired
    private BuffEffectHandler buffEffectHandler;

    public void schedule(AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getAllUnit().forEach(u -> updateBuffs(now, u));
    }

    private void updateBuffs(long now, FightingUnit unit) {
        List<Buff> buffs = unit.getBuffs();

        // 移除过期Buff
        buffs.stream()
                .filter(b -> b.getExpireTime() < now)
                .forEach(b -> removeBuffAndNotify(unit, b));

        // 作用持续buff
        buffs.stream()
                .filter(b -> !b.isOnce())
                .filter(b -> b.getLastEffectTime() + b.getInterval() <= now)
                .forEach(b -> buffEffectHandler.handle(unit, b, now));
    }

    private void removeBuffAndNotify(FightingUnit unit, Buff buff) {
        unit.getBuffs().remove(buff);

        if (unit instanceof Player) {
            Player player = (Player) unit;

            String msg = String.format("%s过期消失", buff.getName());
            player.syncClient(msg);
        }
    }
}
