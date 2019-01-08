package com.wan37.logic.scene.schedule;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.service.FightingUnitMpAdder;
import com.wan37.logic.scene.base.Scene;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 场景自动恢复mp定时器逻辑
 *
 * @author linda
 */
@Service
public class SceneMpRecoverScheduler {

    /**
     * FIXME: 写死场景每30秒恢复1mp
     */
    private static final int MP = 1;

    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(30);

    @Autowired
    private FightingUnitMpAdder fightingUnitMpAdder;

    public void schedule(Scene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (scene.getLastRecoverMpTime() + INTERVAL >= now) {
            return;
        }

        scene.setLastRecoverMpTime(now);
        scene.getPlayers().forEach(this::recoverMp);
    }

    private void recoverMp(Player player) {
        long curMp = player.getMp();
        fightingUnitMpAdder.add(player, MP);

        long result = player.getMp();
        if (curMp != result) {
            String msg = String.format("你自动恢复了%smp", result - curMp);
            player.syncClient(msg);
        }
    }
}
