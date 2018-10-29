package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.player.ScenePlayer;

import java.util.ArrayList;
import java.util.List;

class SceneImpl implements Scene {

    public SceneImpl(SceneCfg sceneCfg) {
        this.sceneCfg = sceneCfg;
    }

    @Override
    public Integer getSceneId() {
        return sceneCfg.getId();
    }

    @Override
    public List<ScenePlayer> getPlayers() {
        return players;
    }

    @Override
    public void addPlayer(ScenePlayer player) {
        players.add(player);
    }

    private final SceneCfg sceneCfg;
    private final List<ScenePlayer> players = new ArrayList<>();
}
