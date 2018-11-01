package com.wan37.logic.scene.service.switch1;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.SceneEnterEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.player.ScenePlayer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneSwitchExec {

    private static final Logger LOG = Logger.getLogger(SceneSwitchExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(SSwitchScene switchScene) {
        Integer sceneId = switchScene.getSceneId();
        if (!checkScene(sceneId)) {
            return;
        }

        playerGlobalManager.findPlayerByChannelId(switchScene.getChannelId())
                .ifPresent(p -> execImpl(sceneId, p));
    }

    private void execImpl(Integer toSceneId, Player player) {
        sceneFacade.switchScene(player, toSceneId);


    }

    private boolean checkScene(Integer sceneId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneId).orElse(null);
        if (sceneCfg == null) {
            LOG.info("找不到目标场景的配置表");
            return false;
        }

        // 其他条件

        return true;
    }
}
