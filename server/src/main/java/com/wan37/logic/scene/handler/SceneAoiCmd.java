package com.wan37.logic.scene.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.service.SceneAoiExec;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 场景信息
 */
@Service
class SceneAoiCmd implements GeneralHandler {

    @Autowired
    private SceneAoiExec sceneAoiExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        sceneAoiExec.exec(player);
    }
}
