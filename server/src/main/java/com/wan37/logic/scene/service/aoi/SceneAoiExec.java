package com.wan37.logic.scene.service.aoi;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.scene.PlayerSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneEncoder;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneAoiExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneEncoder sceneEncoder;

    @Autowired
    private PlayerSceneGetter playerSceneGetter;

    public void exec(SAoiScene aoiScene) {
        Channel channel = aoiScene.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            throw new GeneralErrorExecption("角色未登录，不允许发送该命令");
        }

        AbstractScene scene = playerSceneGetter.get(player);

        String msg = sceneEncoder.encode(scene);
        player.syncClient(msg);
    }
}
