package com.wan37.logic.scene.schedule;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.effect.BuffEffectHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.Scene;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    @Autowired
    private SceneMpRecoverScheduler sceneMpRecoverScheduler;

    @Autowired
    private BuffEffectHandler buffEffectHandler;

    public void schedule(Scene scene) {
        // 刷新怪物
        sceneMonsterScheduler.schedule(scene);

        // 刷新物品
        sceneItemScheduler.schedule(scene);

        // 场景mp回复
        sceneMpRecoverScheduler.schedule(scene);

        // 更新buff
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getPlayers().forEach(p -> updateBuffs(now, p));
    }

    private void updateBuffs(long now, Player player) {
        List<IBuff> buffs = player.getBuffs();

        // 移除过期Buff
        buffs.stream()
                .filter(b -> b.getExpireTime() < now)
                .forEach(buffs::remove);

        // 作用持续buff
        buffs.stream()
                .filter(b -> !b.isOnce())
                .filter(b -> b.getLastEffectTime() + b.getInterval() <= now)
                .forEach(b -> buffEffectHandler.handle(player, b, now));
    }
}
