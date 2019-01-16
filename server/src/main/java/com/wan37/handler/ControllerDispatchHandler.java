package com.wan37.handler;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class ControllerDispatchHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public void handle(GeneralHandler generalHandler, GeneralReqMsg msg) {
        String cmdName = msg.getParamAsString(0);
        if (isLoginOrRegisterCmd(cmdName)) {
            generalHandler.handle(msg);
            return;
        }

        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            channel.writeAndFlush("角色未登录");
            return;
        }

        // 将命令加进场景命令队列
        msg.setPlayer(player);
        AbstractScene scene = sceneActorSceneGetter.get(player);
        scene.addCmd(new Command(generalHandler, msg));
    }

    private boolean isLoginOrRegisterCmd(String cmdName) {
        return "playerLoginCmd".equals(cmdName) ||
                "playerRegisterCmd".equals(cmdName);
    }
}
