package com.wan37.logic.buff;

/**
 * Buff效果
 *
 * @author linda
 */
public enum BuffEffectEnum {

    /**
     * 持续回蓝
     */
    RECOVER_MP(1),

    /**
     * 持续回血
     */
    RECOVER_HP(2),

    /**
     * 一次性护盾
     */
    SHIELD(3),

    /**
     * 无法攻击
     */
    CANNOT_ATTACK(4);

    private Integer id;

    BuffEffectEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
