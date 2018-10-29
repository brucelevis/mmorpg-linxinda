package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.player.ScenePlayer;

import java.util.List;

public interface Scene {

    interface Factory {

        Scene create(SceneCfg cfg);
    }

    Integer getSceneId();

    List<ScenePlayer> getPlayers();

    void addPlayer(ScenePlayer player);
}
