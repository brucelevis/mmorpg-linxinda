package com.wan37.logic.scene.base.impl;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.SceneEnterEvent;
import com.wan37.event.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.base.Scene;
import com.wan37.logic.scene.config.SceneCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class SceneFacadeImpl implements SceneFacade {

    private static final Logger LOG = Logger.getLogger(SceneFacadeImpl.class);

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Override
    public void enterScene(Integer sceneId, Player player) {
        player.setSceneId(sceneId);

        // 进入场景
        sceneGlobalManager.addPlayerInScene(player.getSceneId(), player);

        // 触发进入场景事件
        generalEventListenersManager.fireEvent(new SceneEnterEvent(player));
    }

    @Override
    public void switchScene(Player player, Integer toSceneId) {
        Scene oldScene = sceneGlobalManager.getScene(player.getSceneId());
        SceneCfg oldSceneCfg = oldScene.getSceneCfg();
        if (!oldSceneCfg.getNeighbor().contains(toSceneId)) {
            LOG.info("不可达的场景");
            player.syncClient("不可达的场景");
            return;
        }

        forceSwitchScene(player, toSceneId);
    }

    @Override
    public void forceSwitchScene(Player player, Integer to) {
        leaveScene(player);

        enterScene(to, player);
    }

    @Override
    public void leaveScene(Player player) {
        // 离开场景推送
        generalEventListenersManager.fireEvent(new SceneLeaveEvent(player));
    }
}
