package com.wan37.logic.scene.temporary;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.TemporaryScene;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class TemporarySceneGlobalManager {

    /**
     * map<sceneUid，abstractScene>
     */
    private static Map<Long, TemporaryScene> sceneMap = new ConcurrentHashMap<>();

    /**
     * key：sceneUid
     */
    private static Map<Long, ScheduledFuture> sceneScheduleMap = new HashMap<>();

    /**
     * 场景线程池
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

    public void addScene(TemporaryScene scene) {
        sceneMap.put(scene.getUid(), scene);
    }

    public void addPlayerInScene(Long sceneUid, Player player) {
        TemporaryScene scene = querySceneByUid(sceneUid);
        if (scene == null) {
            return;
        }

        scene.getPlayers().add(player);
    }

    public void removePlayerFromScene(Long sceneUid, Player player) {
        TemporaryScene scene = querySceneByUid(sceneUid);
        if (scene == null) {
            return;
        }

        scene.setPlayers(scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getUid(), player.getUid()))
                .collect(Collectors.toList()));
    }

    public TemporaryScene querySceneByUid(Long sceneUid) {
        return sceneMap.get(sceneUid);
    }

    public void destoryScene(Long sceneUid) {
        sceneScheduleMap.get(sceneUid).cancel(true);
        sceneScheduleMap.remove(sceneUid);
    }
}
