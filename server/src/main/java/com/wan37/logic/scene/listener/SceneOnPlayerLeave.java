package com.wan37.logic.scene.listener;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.event.GeneralEventListener;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.encode.ScenePlayerLeaveNotifyEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        Scene scene = sceneGlobalManager.querySceneById(sceneId);
        if (scene == null) {
            return;
        }

        // 将玩家从场景中移除
        List<Player> players = scene.getPlayers();
        scene.setPlayers(players.stream()
                .filter(p -> !Objects.equals(p.getUid(), playerUid))
                .collect(Collectors.toList()));

        // 推送玩家离开场景通知
        scene.getPlayers().forEach(p -> p.syncClient(notify));
    }
}
