package com.wan37.logic.monster;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.buff.Buff;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.skill.Skill;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 怪物实例
 *
 * @author linda
 */
public class Monster implements FightingUnit {

    private Long uid;
    private Integer sceneId;
    private Long sceneUid;
    private MonsterCfg monsterCfg;

    private volatile long hp;
    private volatile long mp;

    private volatile long deadTime;
    private volatile boolean alive;
    private volatile Long lastAttackId;

    private int baseAttackVal;
    private int baseDefenseVal;

    private Map<Integer, Double> attrs;
    private Map<Integer, Skill> skills;
    private List<Buff> buffs = new CopyOnWriteArrayList<>();

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public MonsterCfg getMonsterCfg() {
        return monsterCfg;
    }

    public void setMonsterCfg(MonsterCfg monsterCfg) {
        this.monsterCfg = monsterCfg;
    }

    public Long getLastAttackId() {
        return lastAttackId;
    }

    public void setLastAttackId(Long lastAttackId) {
        this.lastAttackId = lastAttackId;
    }

    public void setBaseAttackVal(int baseAttackVal) {
        this.baseAttackVal = baseAttackVal;
    }

    public void setBaseDefenseVal(int baseDefenseVal) {
        this.baseDefenseVal = baseDefenseVal;
    }

    public void setAttrs(Map<Integer, Double> attrs) {
        this.attrs = attrs;
    }

    public void setSkills(Map<Integer, Skill> skills) {
        this.skills = skills;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return attrs;
    }

    @Override
    public Map<Integer, Skill> getSkills() {
        return skills;
    }

    @Override
    public List<Buff> getBuffs() {
        return buffs;
    }

    @Override
    public long getBaseAttackVal() {
        return baseAttackVal;
    }

    @Override
    public long getBaseDefenseVal() {
        return baseDefenseVal;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public Integer getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public Long getSceneUid() {
        return sceneUid;
    }

    @Override
    public void setSceneUid(Long uid) {
        this.sceneUid = uid;
    }

    @Override
    public String getName() {
        return monsterCfg.getName();
    }

    @Override
    public long getHp() {
        return hp;
    }

    @Override
    public void setHp(long hp) {
        this.hp = hp;
    }

    @Override
    public long getMaxHp() {
        Integer hpId = AttrEnum.HP.getId();
        if (attrs.containsKey(hpId)) {
            return Math.round(attrs.get(hpId));
        }

        return 0;
    }

    @Override
    public long getMp() {
        return mp;
    }

    @Override
    public void setMp(long mp) {
        this.mp = mp;
    }

    @Override
    public long getMaxMp() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getDeadTime() {
        return deadTime;
    }

    @Override
    public void setDeadTime(long time) {
        this.deadTime = time;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
