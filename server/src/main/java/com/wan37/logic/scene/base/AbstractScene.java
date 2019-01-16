package com.wan37.logic.scene.base;

import com.wan37.handler.Command;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.npc.Npc;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.summoning.Summoning;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 场景抽象父类
 *
 * @author linda
 */
public abstract class AbstractScene implements Runnable {

    public AbstractScene() {
        startCmdThread();
    }

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

    /**
     * 场景命令队列
     */
    protected final Queue<Command> cmdQueue = new ConcurrentLinkedQueue<>();

    /**
     * 场景命令队列执行线程，保证命令有序执行，若命令队列为空，则等待
     */
    private final ExecutorService commandThread = Executors.newSingleThreadExecutor();

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

    public Integer getId() {
        return sceneCfg.getId();
    }

    public String getName() {
        return sceneCfg.getName();
    }

    public Integer getType() {
        return sceneCfg.getType();
    }

    public void notify(String msg) {
        if (msg != null) {
            players.forEach(p -> p.syncClient(msg));
        }
    }

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

    public List<FightingUnit> getAllUnit() {
        List<FightingUnit> unitList = new ArrayList<>();
        unitList.addAll(players);
        unitList.addAll(monsters);
        unitList.addAll(summonings);
        return unitList;
    }

    public void addCmd(Command command) {
        cmdQueue.add(command);

        if (!cmdQueue.isEmpty()) {
            synchronized (cmdQueue) {
                cmdQueue.notify();
            }
        }
    }

    private void startCmdThread() {
        commandThread.execute(() -> {
            waitIfEmpty();

            while (!cmdQueue.isEmpty()) {
                Command cmd = cmdQueue.poll();
                cmd.execute();

                waitIfEmpty();
            }
        });
    }

    private void waitIfEmpty() {
        if (cmdQueue.isEmpty()) {
            try {
                synchronized (cmdQueue) {
                    cmdQueue.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
