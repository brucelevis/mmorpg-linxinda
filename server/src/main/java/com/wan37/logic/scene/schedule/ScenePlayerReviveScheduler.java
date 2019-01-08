package com.wan37.logic.scene.schedule;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 场景玩家复活检查定时器逻辑
 *
 * @author linda
 */
@Service
public class ScenePlayerReviveScheduler {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private PlayerReviveInitializer playerReviveInitializer;

    public void schedule(AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getPlayers().forEach(p -> checkAndRevive(p, now));
    }

    private void checkAndRevive(Player player, long now) {
        if (player.isAlive()) {
            return;
        }

        long interval = TimeUnit.SECONDS.toMillis(10);
        if (player.getDeadTime() + interval > now) {
            return;
        }

        playerReviveInitializer.init(player);

        //FIXME: 写死默认复活安全场景
        Integer toSceneId = 1000;
        sceneFacade.forceSwitchScene(player, toSceneId);
    }
}
