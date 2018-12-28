package com.wan37.logic.scene.schedule;

import com.wan37.logic.buff.entity.IBuff;
import com.wan37.logic.buff.effect.BuffEffectHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScenePlayerBuffsScheduler {

    @Autowired
    private BuffEffectHandler buffEffectHandler;

    public void schedule(AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getPlayers().forEach(p -> updateBuffs(now, p));
    }

    private void updateBuffs(long now, Player player) {
        List<IBuff> buffs = player.getBuffs();

        // 移除过期Buff
        buffs.stream()
                .filter(b -> b.getExpireTime() < now)
                .forEach(b -> removeBuffAndNotify(player, b));

        // 作用持续buff
        buffs.stream()
                .filter(b -> !b.isOnce())
                .filter(b -> b.getLastEffectTime() + b.getInterval() <= now)
                .forEach(b -> buffEffectHandler.handle(player, b, now));
    }

    private void removeBuffAndNotify(Player player, IBuff buff) {
        player.getBuffs().remove(buff);

        String msg = String.format("%s过期消失", buff.getName());
        player.syncClient(msg);
    }
}
