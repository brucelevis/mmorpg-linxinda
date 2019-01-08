package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import com.wan37.logic.scene.SceneTypeEnum;
import com.wan37.logic.scene.base.AbstractTemporaryScene;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 玩家监听登录事件
 */
@Service
class PlayerOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private PlayerReviveInitializer playerReviver;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        if (!player.isAlive()) {
            // 复活
            playerReviver.init(player);
        }

        SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId()).orElse(null);
        if (sceneCfg == null) {
            return;
        }

        if (Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            // 普通场景，进入
            sceneFacade.enterScene(player.getSceneId(), player);
            return;
        }

        // 离线前在临时场景
        AbstractTemporaryScene scene = temporarySceneGlobalManager.querySceneByUid(player.getSceneUid());
        if (scene == null) {
            // 临时场景过期消失，传送到安全村落
            sceneFacade.enterScene(1000, player);
            return;
        }

        // 临时场景还没过期，重新进入
        temporarySceneGlobalManager.addPlayerInScene(scene.getUid(), player);

        // TODO: 推送信息
    }
}
