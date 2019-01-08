package com.wan37.logic.scene.handler;

import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.service.SceneAoiExec;
import com.wan37.server.GeneralReqMsg;
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
            throw new GeneralErrorException("角色未登录，不允许发送该命令");
        }

        sceneAoiExec.exec(player);
    }
}
