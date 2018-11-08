package com.wan37.logic.monster;

import com.wan37.logic.monster.config.MonsterCfg;

public class Monster implements IMonster {

    private Long uid;
    private MonsterCfg monsterCfg;

    private double hp;
    private double mp;

    private long deadTime;
    private boolean alive;
    private int cd;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public MonsterCfg getMonsterCfg() {
        return monsterCfg;
    }

    public void setMonsterCfg(MonsterCfg monsterCfg) {
        this.monsterCfg = monsterCfg;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(long deadTime) {
        this.deadTime = deadTime;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    @Override
    public Integer getCfgId() {
        return monsterCfg.getId();
    }

    @Override
    public String getName() {
        return monsterCfg.getName();
    }
}
