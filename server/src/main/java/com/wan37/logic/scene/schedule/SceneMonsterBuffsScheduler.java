package com.wan37.logic.scene.schedule;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.BuffEffectHandler;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SceneMonsterBuffsScheduler {

    @Autowired
    private BuffEffectHandler buffEffectHandler;

    public void schedule(AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getMonsters().forEach(m -> updateBuffs(now, m));
    }

    private void updateBuffs(long now, Monster monster) {
        List<IBuff> buffs = monster.getBuffs();

        // 移除过期Buff
        buffs.stream()
                .filter(b -> b.getExpireTime() < now)
                .forEach(buffs::remove);

        // 作用持续buff
        buffs.stream()
                .filter(b -> !b.isOnce())
                .filter(b -> b.getLastEffectTime() + b.getInterval() <= now)
                .forEach(b -> buffEffectHandler.handle(monster, b, now));
    }
}