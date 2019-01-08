package com.wan37.logic.scene;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.SceneEnterEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractTemporaryScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 临时场景全局管理器
 *
 * @author linda
 */
@Service
public class TemporarySceneGlobalManager {

    /**
     * map<sceneUid，abstractScene>
     */
    private static Map<Long, AbstractTemporaryScene> sceneMap = new ConcurrentHashMap<>();

    /**
     * key：sceneUid
     */
    private static Map<Long, ScheduledFuture> sceneScheduleMap = new HashMap<>();

    /**
     * 场景线程池
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public Collection<AbstractTemporaryScene> getAllScenes() {
        return sceneMap.values();
    }

    public void addScene(AbstractTemporaryScene scene) {
        sceneMap.put(scene.getUid(), scene);

        // 启动场景心跳
        ScheduledFuture<?> schedule = scheduledExecutorService.scheduleAtFixedRate(scene, 200, 200, TimeUnit.MILLISECONDS);
        sceneScheduleMap.put(scene.getUid(), schedule);
    }

    public void addPlayerInScene(Long sceneUid, Player player) {
        AbstractTemporaryScene scene = querySceneByUid(sceneUid);
        if (scene == null) {
            return;
        }

        player.setSceneId(scene.getId());
        player.setSceneUid(sceneUid);
        scene.getPlayers().add(player);

        // 触发进入场景事件
        generalEventListenersManager.fireEvent(new SceneEnterEvent(player));
    }

    public void removePlayerFromScene(Long sceneUid, Player player) {
        AbstractTemporaryScene scene = querySceneByUid(sceneUid);
        if (scene == null) {
            return;
        }

        scene.setPlayers(scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getUid(), player.getUid()))
                .collect(Collectors.toList()));
    }

    public AbstractTemporaryScene querySceneByUid(Long sceneUid) {
        return sceneMap.get(sceneUid);
    }

    public void destroyScene(Long sceneUid) {
        ScheduledFuture scheduledFuture = sceneScheduleMap.get(sceneUid);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }

        sceneScheduleMap.remove(sceneUid);
        sceneMap.remove(sceneUid);
    }
}
