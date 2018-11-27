package com.wan37.logic.monster;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.skill.ISkill;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Monster implements IMonster {

    private Long uid;
    private Integer sceneId;
    private MonsterCfg monsterCfg;

    private long hp;

    private long deadTime;
    private boolean alive;

    private Map<Integer, Double> attrs;
    private List<ISkill> skills;

    private int baseAttackVal;
    private int baseDefenseVal;

    private Long lastAttackId;
    private List<IBuff> buffs = new CopyOnWriteArrayList<>();

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

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
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

    public Map<Integer, Double> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, Double> attrs) {
        this.attrs = attrs;
    }

    public int getBaseAttackVal() {
        return baseAttackVal;
    }

    public void setBaseAttackVal(int baseAttackVal) {
        this.baseAttackVal = baseAttackVal;
    }

    public int getBaseDefenseVal() {
        return baseDefenseVal;
    }

    public void setBaseDefenseVal(int baseDefenseVal) {
        this.baseDefenseVal = baseDefenseVal;
    }

    public Long getLastAttackId() {
        return lastAttackId;
    }

    public void setLastAttackId(Long lastAttackId) {
        this.lastAttackId = lastAttackId;
    }

    public List<IBuff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<IBuff> buffs) {
        this.buffs = buffs;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public List<ISkill> getSkills() {
        return skills;
    }

    public void setSkills(List<ISkill> skills) {
        this.skills = skills;
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
