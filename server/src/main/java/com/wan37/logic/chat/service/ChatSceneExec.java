package com.wan37.logic.chat.service;

import com.wan37.logic.chat.ChatTypeEnum;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatSceneExec {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    public void exec(Player player, String msg) {
        Scene scene = sceneGlobalManager.getScene(player.getSceneId());

        String content = String.format("【%s】 [%s]：%s", ChatTypeEnum.CHAT_TYPE_SCENE.getName(), player.getName(), msg);
        scene.getPlayers().forEach(p -> p.syncClient(content));
    }
}
