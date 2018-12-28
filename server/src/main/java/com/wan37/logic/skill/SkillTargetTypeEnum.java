package com.wan37.logic.skill;

public enum SkillTargetTypeEnum {

    SKILL_TARGET_TYPE_1(1, "自己"),
    SKILL_TARGET_TYPE_2(2, "友方"),
    SKILL_TARGET_TYPE_3(3, "敌人");

    private Integer id;
    private String name;

    SkillTargetTypeEnum(Integer id, String name) {
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
