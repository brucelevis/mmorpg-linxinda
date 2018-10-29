package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgImpl;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class SceneGlobalManager {

    /**
     * @see Scene#getSceneId()
     */
    private static ConcurrentMap<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

    @Autowired
    private Scene.Factory factory;

    public void addPlayer(Integer sceneId, ScenePlayer scenePlayer) {
        // TODO: 检查sceneId的合法性

        Scene scene = sceneMap.get(sceneId);
        if (scene == null) {
            SceneCfg sceneCfg = loadSceneCfg(sceneId);
            scene = factory.create(sceneCfg);

            sceneMap.putIfAbsent(sceneId, scene);
        }

        scene.addPlayer(scenePlayer);
    }

    @Deprecated
    private SceneCfg loadSceneCfg(Integer id) {
        return new SceneCfgImpl(id);
    }
}
