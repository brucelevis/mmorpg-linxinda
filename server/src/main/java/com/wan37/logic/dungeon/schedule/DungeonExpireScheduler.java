package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DungeonExpireScheduler {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void schedule(DungeonScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (now < scene.getExpireTime()) {
            return;
        }

        String msg = String.format("%s副本挑战失败", scene.getName());
        scene.getPlayers().forEach(p -> syncTipAndLeave(p, msg));

        temporarySceneGlobalManager.destoryScene(scene.getUid());
    }

    private void syncTipAndLeave(Player player, String tip) {
        player.syncClient(tip);
        sceneFacade.enterScene(1000, player);
    }
}
