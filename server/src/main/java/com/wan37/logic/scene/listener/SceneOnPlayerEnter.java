package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.SceneEnterEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneEncoder;
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
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Autowired
    private SceneEncoder sceneEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void execute(SceneEnterEvent event) {
        Player player = event.getPlayer();

        AbstractScene scene = sceneActorSceneGetter.get(player);
        String msg = "玩家进入场景通知|" + playerInfoEncoder.encode(player);

        // 通知场景里除自己的所有玩家
        scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getUid(), player.getUid()))
                .forEach(p -> p.syncClient(msg));

        // 推送当前场景信息给玩家
        String sceneMsg = sceneEncoder.encode(scene);
        player.syncClient(sceneMsg);
    }
}
