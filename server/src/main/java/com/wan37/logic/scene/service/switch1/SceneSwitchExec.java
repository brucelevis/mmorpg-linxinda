package com.wan37.logic.scene.service.switch1;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.SceneEnterEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneSwitchExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void exec(SSwitchScene switchScene) {
        Integer sceneId = switchScene.getSceneId();
        //TODO: 检查场景id合法性

        playerGlobalManager.findPlayerByChannelId(switchScene.getChannelId())
                .ifPresent(p -> execImpl(sceneId, p));
    }

    private void execImpl(Integer sceneId, Player player) {
        Long playerUid = player.getUid();

        Scene oldScene = sceneGlobalManager.getScene(player.getSceneId());
        ScenePlayer scenePlayer = oldScene.getPlayer(playerUid);
        if (scenePlayer == null) {
            return;
        }

        player.setSceneId(sceneId);
        player.save();

        // 离开场景推送
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(oldScene.getSceneId(), playerUid));

        // 移除旧场景里的玩家
        oldScene.removePlayer(playerUid);

        // 加入新场景
        Scene newScene = sceneGlobalManager.getScene(sceneId);
        newScene.addPlayer(scenePlayer);

        // 加入场景推送
        genernalEventListenersManager.fireEvent(new SceneEnterEvent(playerUid));
    }
}
