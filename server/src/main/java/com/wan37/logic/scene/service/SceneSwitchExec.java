package com.wan37.logic.scene.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.config.SceneCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class SceneSwitchExec {

    private static final Logger LOG = Logger.getLogger(SceneSwitchExec.class);

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(Player player, Integer sceneId) {
        if (!checkScene(sceneId)) {
            return;
        }

        if (Objects.equals(player.getSceneId(), sceneId)) {
            player.syncClient("已经在目标场景里了");
            return;
        }

        sceneFacade.switchScene(player, sceneId);
    }

    private boolean checkScene(Integer sceneId) {
        SceneCfg sceneCfg = configLoader.load(SceneCfg.class, sceneId).orElse(null);
        if (sceneCfg == null) {
            LOG.info("找不到目标场景的配置表");
            return false;
        }

        // 其他条件

        return true;
    }
}
