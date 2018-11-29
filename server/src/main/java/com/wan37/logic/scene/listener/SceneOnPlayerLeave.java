package com.wan37.logic.scene.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.SceneTypeEnum;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    private SceneCfgLoader sceneCfgLoader;

    @Override
    public void execute(SceneLeaveEvent sceneLeaveEvent) {
        Player player = sceneLeaveEvent.getPlayer();
        SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId()).orElse(null);
        if (sceneCfg == null) {
            return;
        }

        AbstractScene scene;
        Integer sceneId = sceneCfg.getId();
        if (Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            // 在普通场景
            scene = sceneGlobalManager.querySceneById(sceneId);

            // 将玩家从场景中移除
            sceneGlobalManager.removePlayerFromScene(sceneId, player.getUid());
        } else {
            // 在临时场景
            scene = temporarySceneGlobalManager.querySceneByUid(player.getSceneUid());

            // 将玩家从场景中移除
            temporarySceneGlobalManager.removePlayerFromScene(player.getSceneUid(), player);
        }

        // 推送玩家离开场景通知
        String msg = String.format("玩家离开场景通知| 名字：%s (playerUid：%s)\n", player.getName(), player.getUid());
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }
}
