package com.wan37.config.entity;

public class SkillCfgExcel {

    private Integer id;
    private String name;
    private String desc;
    private String cd;
    private int maxLevel;
    private String effectValue;
    private String costMp;
    private String buffs;
    private boolean effectAll;
    private Integer effectLogic;
    private Integer targetType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getCostMp() {
        return costMp;
    }

    public void setCostMp(String costMp) {
        this.costMp = costMp;
    }

    public String getBuffs() {
        return buffs;
    }

    public void setBuffs(String buffs) {
        this.buffs = buffs;
    }

    public boolean isEffectAll() {
        return effectAll;
    }

    public void setEffectAll(boolean effectAll) {
        this.effectAll = effectAll;
    }

    public String getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(String effectValue) {
        this.effectValue = effectValue;
    }

    public Integer getEffectLogic() {
        return effectLogic;
    }

    public void setEffectLogic(Integer effectLogic) {
        this.effectLogic = effectLogic;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
}
