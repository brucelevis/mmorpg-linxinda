package com.wan37.logic.skill;

/**
 * 技能目标类型枚举
 *
 * @author linda
 */
public enum SkillTargetTypeEnum {

    /**
     * 自己
     */
    MYSELF(1),

    /**
     * 友方
     */
    TEAM(2),

    /**
     * 敌人
     */
    ENEMY(3);

    private Integer id;

    SkillTargetTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
