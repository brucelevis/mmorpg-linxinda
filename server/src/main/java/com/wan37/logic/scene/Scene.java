package com.wan37.logic.scene;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.item.SceneItem;
import com.wan37.logic.scene.schedule.SceneScheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scene implements IScene, Runnable {

    private SceneCfg sceneCfg;

    private List<Player> players;

    private List<Monster> monsters;

    private List<Npc> npcs;

    /**
     * 地上奖励
     */
    private Map<Long, SceneItem> items = new ConcurrentHashMap<>();

    private SceneScheduler sceneScheduler;

    /**
     * 上次场景恢复mp触发时间
     */
    private volatile long lastRecoverMpTime;

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

    public void setSceneScheduler(SceneScheduler sceneScheduler) {
        this.sceneScheduler = sceneScheduler;
    }

    public Map<Long, SceneItem> getItems() {
        return items;
    }

    public void setItems(Map<Long, SceneItem> items) {
        this.items = items;
    }

    public long getLastRecoverMpTime() {
        return lastRecoverMpTime;
    }

    public void setLastRecoverMpTime(long lastRecoverMpTime) {
        this.lastRecoverMpTime = lastRecoverMpTime;
    }

    @Override
    public Integer getCfgId() {
        return sceneCfg.getId();
    }

    @Override
    public void run() {
        try {
            sceneScheduler.schedule(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
