package com.wan37.logic.summoning;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.buff.entity.Buff;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.logic.summoning.config.SummoningCfg;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Summoning implements FightingUnit {

    private Long uid;
    private Integer sceneId;
    private Long sceneUid;
    private SummoningCfg summoningCfg;
    private Long belongUid;

    private volatile long hp;
    private volatile long mp;

    private int baseAttackVal;
    private int baseDefenseVal;

    private Map<Integer, Double> attrs;
    private Map<Integer, ISkill> skills;
    private List<Buff> buffs = new CopyOnWriteArrayList<>();

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return attrs;
    }

    @Override
    public Map<Integer, ISkill> getSkills() {
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
        return summoningCfg.getName();
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
        Integer hpId = AttrEnum.ATTR_HP.getId();
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
        return 0;
    }

    @Override
    public void setDeadTime(long time) {
        // NOOP
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void setAlive(boolean alive) {
        // NOOP
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public SummoningCfg getSummoningCfg() {
        return summoningCfg;
    }

    public void setSummoningCfg(SummoningCfg summoningCfg) {
        this.summoningCfg = summoningCfg;
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

    public void setSkills(Map<Integer, ISkill> skills) {
        this.skills = skills;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public Long getBelongUid() {
        return belongUid;
    }

    public void setBelongUid(Long belongUid) {
        this.belongUid = belongUid;
    }
}
