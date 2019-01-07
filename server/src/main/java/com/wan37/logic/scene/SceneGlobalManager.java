package com.wan37.logic.scene;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.init.SceneCreator;
import com.wan37.logic.scene.base.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class SceneGlobalManager {

    /**
     * 普通场景
     * map<sceneCfgId，scene>
     */
    private static Map<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

    private static Map<Integer, ScheduledFuture> sceneScheduleMap = new HashMap<>();

    //FIXME:
    /**
     * 场景线程池
     * <p>
     * 线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
     * 的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
     * 说明：Executors 返回的线程池对象的弊端如下：
     * 1）FixedThreadPool 和 SingleThreadPool:
     * 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
     * 2）CachedThreadPool 和 ScheduledThreadPool:
     * 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM
     */
    @Deprecated
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

    @Autowired
    private SceneCreator sceneCreator;

    public Collection<Scene> getAllScenes() {
        return sceneMap.values();
    }

    public void addPlayerInScene(Integer sceneId, Player player) {
        // TODO: 检查sceneId的合法性

        Scene scene = getScene(sceneId);
        scene.getPlayers().add(player);
    }

    public void removePlayerFromScene(Integer sceneId, Long playerUid) {
        Scene scene = querySceneById(sceneId);
        if (scene == null) {
            return;
        }

        scene.setPlayers(scene.getPlayers().stream()
                .filter(p -> !Objects.equals(p.getUid(), playerUid))
                .collect(Collectors.toList()));
    }

    public Scene querySceneById(Integer sceneId) {
        return sceneMap.get(sceneId);
    }

    public Scene getScene(Integer sceneId) {
        Scene scene = querySceneById(sceneId);
        if (scene != null) {
            return scene;
        }

        Scene newScene = sceneCreator.create(sceneId);
        sceneMap.put(sceneId, newScene);

        // 启动场景心跳
        ScheduledFuture<?> schedule = scheduledExecutorService.scheduleAtFixedRate(newScene, 200, 200, TimeUnit.MILLISECONDS);
        sceneScheduleMap.put(sceneId, schedule);

        return newScene;
    }
}
