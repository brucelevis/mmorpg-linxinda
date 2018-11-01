package com.wan37.logic.scene;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.init.SceneCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SceneGlobalManager {

    /**
     * map<sceneCfgId,scene>>
     */
    private static Map<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

    @Autowired
    private SceneCreator sceneCreator;

    public void addPlayerInScene(Integer sceneId, Player player) {
        // TODO: 检查sceneId的合法性

        Scene scene = getScene(sceneId);
        scene.getPlayers().add(player);
    }

    public void removePlayerFromScene(Integer sceneId, Player player) {
        Scene scene = querySceneById(sceneId);
        if (scene == null) {
            return;
        }

        scene.getPlayers().remove(player);
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

        return newScene;
    }
}
