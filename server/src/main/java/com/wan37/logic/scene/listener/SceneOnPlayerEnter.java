package com.wan37.logic.scene.listener;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneEnterEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.ScenePlayerEnterNotifyEncoder;
import com.wan37.logic.scene.player.ScenePlayer;
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
    private ScenePlayerEnterNotifyEncoder scenePlayerEnterNotifyEncoder;

    @Override
    public void execute(SceneEnterEvent event) {
        Long playerUid = event.getPlayerUid();
        playerGlobalManager.findPlayerByUid(playerUid)
                .ifPresent(this::notify);
    }

    private void notify(Player player) {
        Scene scene = sceneGlobalManager.getScene(player.getSceneId());

        Long uid = player.getUid();
        ScenePlayer scenePlayer = scene.getPlayers().stream()
                .filter(p -> Objects.equals(p.getPlayerUid(), uid))
                .findAny()
                .orElseThrow(() -> new RuntimeException("场景通知找不到玩家自己"));

        GeneralResponseDto notify = scenePlayerEnterNotifyEncoder.encode(ResultCode.SCENE_PLAYER_ENTER, scenePlayer);

        scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getPlayerUid(), uid))
                .forEach(p -> p.notify(notify));
    }
}
