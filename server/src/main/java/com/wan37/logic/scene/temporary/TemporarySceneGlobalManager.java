package com.wan37.logic.scene.temporary;

import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Service
public class TemporarySceneGlobalManager {

    /**
     * map<uid，abstractScene>
     */
    private static Map<Long, AbstractScene> sceneMap = new ConcurrentHashMap<>();

    private static Map<Long, ScheduledFuture> sceneScheduleMap = new HashMap<>();

    /**
     * 场景线程池
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
}
