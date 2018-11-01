package com.wan37.logic.scene;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.SceneEnterEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.scene.config.SceneCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneFacadeImpl implements SceneFacade {

    private static final Logger LOG = Logger.getLogger(SceneFacadeImpl.class);

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void enterScene(Integer sceneId, Player player) {
        player.setSceneId(sceneId);
        playerDao.save(player.getPlayerDb());

        // 进入场景
        sceneGlobalManager.addPlayerInScene(player.getSceneId(), player);

        // 触发进入场景事件
        genernalEventListenersManager.fireEvent(new SceneEnterEvent(player.getUid()));
    }

    @Override
    public void switchScene(Player player, Integer toSceneId) {
        Long playerUid = player.getUid();

        Scene oldScene = sceneGlobalManager.getScene(player.getSceneId());
        SceneCfg oldSceneCfg = oldScene.getSceneCfg();
        if (!oldSceneCfg.getNeighbor().contains(toSceneId)) {
            LOG.info("不可达的场景");
            player.syncClient("不可达的场景");
            return;
        }

        // 离开场景推送
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(oldScene.getCfgId(), playerUid));

        // 移除旧场景里的玩家
        sceneGlobalManager.removePlayerFromScene(oldScene.getCfgId(), player);

        enterScene(toSceneId, player);
    }
}