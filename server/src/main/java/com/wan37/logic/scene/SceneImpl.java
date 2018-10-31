package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.player.ScenePlayer;

import java.util.*;

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
        return new ArrayList<>(players.values());
    }

    @Override
    public void addPlayer(ScenePlayer player) {
        players.put(player.getPlayerUid(), player);
    }

    @Override
    public ScenePlayer getPlayer(Long playerUid) {
        return players.get(playerUid);
    }

    @Override
    public void removePlayer(Long playerUid) {
        players.remove(playerUid);
    }

    @Override
    public SceneCfg getSceneCfg() {
        return sceneCfg;
    }

    private final SceneCfg sceneCfg;
    private final Map<Long, ScenePlayer> players = new HashMap<>();
}
