package com.wan37.logic.scene.base;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.item.SceneItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractScene implements IScene {

    protected SceneCfg sceneCfg;

    protected List<Player> players;

    protected List<Monster> monsters;

    protected List<Npc> npcs;

    /**
     * 地上奖励
     */
    protected Map<Long, SceneItem> items = new ConcurrentHashMap<>();

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

    public List<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<Npc> npcs) {
        this.npcs = npcs;
    }

    public Map<Long, SceneItem> getItems() {
        return items;
    }

    public void setItems(Map<Long, SceneItem> items) {
        this.items = items;
    }

    @Override
    public Integer getId() {
        return sceneCfg.getId();
    }

    @Override
    public String getName() {
        return sceneCfg.getName();
    }

    @Override
    public boolean canAttack() {
        return sceneCfg.canAttack();
    }

    @Override
    public Integer getType() {
        return sceneCfg.getType();
    }
}
