package com.wan37.logic.scene.base;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.summoning.Summoning;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractScene implements IScene {

    protected Long uid;

    protected SceneCfg sceneCfg;

    protected List<Player> players;

    protected List<Monster> monsters;

    protected List<Npc> npcs;

    protected List<Summoning> summonings;

    /**
     * 地上奖励
     */
    protected Map<Long, SceneItem> items = new ConcurrentHashMap<>();

    @Override
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

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

    public List<Summoning> getSummonings() {
        return summonings;
    }

    public void setSummonings(List<Summoning> summonings) {
        this.summonings = summonings;
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

    @Override
    public void notify(String msg) {
        if (msg != null) {
            players.forEach(p -> p.syncClient(msg));
        }
    }

    @Override
    public FightingUnit getTargetUnit(Long uid) {
        Optional<Player> player = findPlayer(uid);
        if (player.isPresent()) {
            return player.get();
        }

        Optional<Monster> monster = findMonster(uid);
        if (monster.isPresent()) {
            return monster.get();
        }

        Optional<Summoning> summoning = findSummoning(uid);
        return summoning.orElse(null);
    }

    private Optional<Player> findPlayer(Long uid) {
        return players.stream()
                .filter(p -> Objects.equals(p.getUid(), uid))
                .findAny();
    }

    private Optional<Monster> findMonster(Long uid) {
        return monsters.stream()
                .filter(m -> Objects.equals(m.getUid(), uid))
                .findAny();
    }

    private Optional<Summoning> findSummoning(Long uid) {
        return summonings.stream()
                .filter(s -> Objects.equals(s.getUid(), uid))
                .findAny();
    }
}
