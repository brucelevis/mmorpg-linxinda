package com.wan37.logic.scene;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;

import java.util.List;

public class Scene implements IScene {

    private SceneCfg sceneCfg;

    private List<Player> players;

    private List<Monster> monsters;

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

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @Override
    public Integer getCfgId() {
        return sceneCfg.getId();
    }
}
