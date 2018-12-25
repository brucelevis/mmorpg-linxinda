package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArenaExpireScheduler {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void schedule(ArenaScene scene) {
        if (scene.getPlayers().size() == 0) {
            // 没人了
            temporarySceneGlobalManager.destoryScene(scene.getUid());
            return;
        }

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (now < scene.getExpireTime()) {
            return;
        }

        // 竞技场决斗时间结束
        scene.getPlayers().forEach(this::syncTipAndLeave);
        temporarySceneGlobalManager.destoryScene(scene.getUid());
    }

    private void syncTipAndLeave(Player player) {
        player.syncClient("平手");
        sceneFacade.enterScene(1000, player);
    }
}
