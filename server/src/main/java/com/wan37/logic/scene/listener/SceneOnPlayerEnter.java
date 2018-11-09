package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneEnterEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 玩家进入场景监听
 */
@Service
class SceneOnPlayerEnter implements GeneralEventListener<SceneEnterEvent> {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Override
    public void execute(SceneEnterEvent event) {
        Long playerUid = event.getPlayerUid();
        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        if (player == null) {
            return;
        }

        Scene scene = sceneGlobalManager.getScene(player.getSceneId());
        String msg = "玩家进入场景通知|" + playerInfoEncoder.encode(player.getPlayerDb());

        // 通知场景里除自己的所有玩家
        scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getUid(), player.getUid()))
                .forEach(p -> p.syncClient(msg));
    }
}
