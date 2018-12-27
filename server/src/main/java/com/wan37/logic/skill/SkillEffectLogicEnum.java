package com.wan37.logic.skill;

public enum SkillEffectLogicEnum {

    SKILL_EFFECT_LOGIC_1(1, "攻击"),
    SKILL_EFFECT_LOGIC_2(2, "自己加Buff"),
    SKILL_EFFECT_LOGIC_3(3, "友方加Buff"),
    SKILL_EFFECT_LOGIC_4(4, "敌人加Buff"),
    SKILL_EFFECT_LOGIC_5(5, "自己治疗"),
    SKILL_EFFECT_LOGIC_6(6, "友方治疗");

    private Integer id;
    private String name;

    SkillEffectLogicEnum(Integer id, String name) {
        this.setId(id);
        this.setName(name);
    }

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
}
