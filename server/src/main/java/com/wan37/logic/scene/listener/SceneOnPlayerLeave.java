package com.wan37.logic.scene.listener;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.ScenePlayerLeaveNotifyEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 离开场景监听
 */
@Service
class SceneOnPlayerLeave implements GeneralEventListener<SceneLeaveEvent> {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private ScenePlayerLeaveNotifyEncoder scenePlayerLeaveNotifyEncoder;

    @Override
    public void execute(SceneLeaveEvent sceneLeaveEvent) {
        Long playerUid = sceneLeaveEvent.getPlayerUid();
        Integer sceneId = sceneLeaveEvent.getSceneId();

        GeneralResponseDto notify = scenePlayerLeaveNotifyEncoder.encode(ResultCode.SCENE_PLAYER_LEAVE, playerUid);

        Scene scene = sceneGlobalManager.getScene(sceneId);
        scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getPlayerUid(), playerUid))
                .forEach(p -> p.notify(notify));
    }
}
