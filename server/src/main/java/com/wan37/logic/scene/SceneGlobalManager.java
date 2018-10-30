package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
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
    private Scene.Factory sceneFactory;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    public void addPlayer(Integer sceneId, ScenePlayer scenePlayer) {
        // TODO: 检查sceneId的合法性

        Scene scene = getScene(sceneId);
        scene.addPlayer(scenePlayer);
    }

    public Scene getScene(Integer sceneId) {
        if (sceneMap.containsKey(sceneId)) {
            return sceneMap.get(sceneId);
        }

        Scene scene = createScene(sceneId);
        sceneMap.putIfAbsent(sceneId, scene);

        return scene;
    }

    private Scene createScene(Integer sceneId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneId)
                .orElseThrow(() -> new RuntimeException("找不到SceneCfg"));
        return sceneFactory.create(sceneCfg);
    }
}
