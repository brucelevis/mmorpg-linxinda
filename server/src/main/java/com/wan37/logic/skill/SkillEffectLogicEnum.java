package com.wan37.logic.skill;

/**
 * 技能生效逻辑枚举
 *
 * @author linda
 */
public enum SkillEffectLogicEnum {

    /**
     * 攻击
     */
    ATTACK(1),

    /**
     * 加Buff
     */
    ADD_BUFF(2),

    /**
     * 治疗
     */
    CURE(3),

    /**
     * 召唤兽
     */
    SUMMONING(4);

    private Integer id;

    SkillEffectLogicEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
