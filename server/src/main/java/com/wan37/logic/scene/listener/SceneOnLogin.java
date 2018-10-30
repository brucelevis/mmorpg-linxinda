package com.wan37.logic.scene.listener;

import com.wan37.common.notify.ScenePlayerNotify;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 玩家登录场景监听
 */
@Service
class SceneOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Override
    public void execute(LoginEvent event) {
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

        ScenePlayerNotify notify = encodeNotify(scenePlayer);

        scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getPlayerUid(), uid))
                .forEach(p -> p.notify(notify));
    }

    private ScenePlayerNotify encodeNotify(ScenePlayer player) {
        ScenePlayerNotify dto = new ScenePlayerNotify();

        dto.setPlayerUid(player.getPlayerUid());
        dto.setName(player.getPlayerName());
        dto.setFactionId(player.getFactionId());
        dto.setLevel(player.getLevel());

        return dto;
    }
}
