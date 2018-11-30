package com.wan37.logic.chat;

import com.wan37.logic.scene.base.TemporaryScene;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.scene.SceneGlobalManager;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChatFacade {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void chatToWorld(String content) {
        sceneGlobalManager.getAllScenes().stream()
                .map(Scene::getPlayers)
                .flatMap(Collection::stream)
                .forEach(p -> p.syncClient(content));

        temporarySceneGlobalManager.getAllScenes().stream()
                .map(TemporaryScene::getPlayers)
                .flatMap(Collection::stream)
                .forEach(p -> p.syncClient(content));
    }
}
