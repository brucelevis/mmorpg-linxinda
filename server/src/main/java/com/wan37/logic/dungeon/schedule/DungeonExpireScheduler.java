package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.scene.DungeonSceneAbstract;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 副本过期
 *
 * @author linda
 */
@Service
public class DungeonExpireScheduler {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void schedule(DungeonSceneAbstract scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (now < scene.getExpireTime()) {
            return;
        }

        String msg = String.format("%s副本挑战失败", scene.getName());
        scene.getPlayers().forEach(p -> syncTipAndLeave(p, msg));

        temporarySceneGlobalManager.destroyScene(scene.getUid());
    }

    private void syncTipAndLeave(Player player, String tip) {
        player.syncClient(tip);
        sceneFacade.enterScene(1000, player);
    }
}
