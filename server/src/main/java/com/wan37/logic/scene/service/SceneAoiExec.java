package com.wan37.logic.scene.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class SceneAoiExec {

    @Autowired
    private SceneEncoder sceneEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public void exec(Player player) {
        AbstractScene scene = sceneActorSceneGetter.get(player);

        String msg = sceneEncoder.encode(scene);
        player.syncClient(msg);
    }
}
