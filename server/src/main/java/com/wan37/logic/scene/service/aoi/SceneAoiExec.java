package com.wan37.logic.scene.service.aoi;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.SceneEncoder;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneAoiExec {

    private static final Logger LOG = Logger.getLogger(SceneAoiExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SceneEncoder sceneEncoder;

    public void exec(SAoiScene aoiScene) {
        Channel channel = aoiScene.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            LOG.info("角色未登录，不允许发送该命令");
            return;
        }

        Integer sceneId = player.getSceneId();
        Scene scene = sceneGlobalManager.getScene(sceneId);

        String msg = sceneEncoder.encode(scene);
        player.syncClient(msg);
    }
}
