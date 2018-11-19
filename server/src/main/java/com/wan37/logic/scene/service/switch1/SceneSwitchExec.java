package com.wan37.logic.scene.service.switch1;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SceneSwitchExec {

    private static final Logger LOG = Logger.getLogger(SceneSwitchExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(SSwitchScene switchScene) {
        Integer sceneId = switchScene.getSceneId();
        Channel channel = switchScene.getChannel();

        if (!checkScene(sceneId)) {
            return;
        }

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        if (Objects.equals(player.getSceneId(), sceneId)) {
            player.syncClient("已经在目标场景里了");
            return;
        }

        sceneFacade.switchScene(player, sceneId);
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
