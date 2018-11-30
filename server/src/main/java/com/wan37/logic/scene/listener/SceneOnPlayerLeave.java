package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家离开场景监听
 */
@Service
class SceneOnPlayerLeave implements GeneralEventListener<SceneLeaveEvent> {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void execute(SceneLeaveEvent sceneLeaveEvent) {
        Player player = sceneLeaveEvent.getPlayer();
        AbstractScene scene = sceneActorSceneGetter.get(player);
        if (scene instanceof Scene) {
            // 将玩家从场景中移除
            sceneGlobalManager.removePlayerFromScene(scene.getId(), player.getUid());
        } else {
            // 将玩家从临时场景中移除
            temporarySceneGlobalManager.removePlayerFromScene(player.getSceneUid(), player);
        }

        // 推送玩家离开场景通知
        String msg = String.format("玩家离开场景通知| 名字：%s (playerUid：%s)\n", player.getName(), player.getUid());
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }
}
