package com.wan37.logic.buff;

/**
 * Buff目标
 *
 * @author linda
 */
public enum BuffTargetEnum {

    /**
     * 自己
     */
    MYSELF(1),

    /**
     * 敌人
     */
    ENEMY(2);

    private Integer id;

    BuffTargetEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}