package com.wan37.logic.scene;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;

import java.util.List;

public class Scene implements IScene {

    private SceneCfg sceneCfg;

    private List<Player> players;

    public SceneCfg getSceneCfg() {
        return sceneCfg;
    }

    public void setSceneCfg(SceneCfg sceneCfg) {
        this.sceneCfg = sceneCfg;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public Integer getCfgId() {
        return sceneCfg.getId();
    }
}
