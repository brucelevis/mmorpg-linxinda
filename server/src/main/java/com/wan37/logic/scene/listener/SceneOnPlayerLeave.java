package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 离开场景监听
 */
@Service
class SceneOnPlayerLeave implements GeneralEventListener<SceneLeaveEvent> {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void execute(SceneLeaveEvent sceneLeaveEvent) {
        Long playerUid = sceneLeaveEvent.getPlayerUid();
        Integer sceneId = sceneLeaveEvent.getSceneId();

        Scene scene = sceneGlobalManager.querySceneById(sceneId);
        if (scene == null) {
            return;
        }

        // 将玩家从场景中移除
        List<Player> players = scene.getPlayers();
        scene.setPlayers(players.stream()
                .filter(p -> !Objects.equals(p.getUid(), playerUid))
                .collect(Collectors.toList()));

        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        if (player == null) {
            return;
        }

        // 推送玩家离开场景通知
        String msg = String.format("玩家离开场景通知| 名字：%s (playerUid：%s)\n", player.getPlayerDb().getName(), playerUid);
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }
}
