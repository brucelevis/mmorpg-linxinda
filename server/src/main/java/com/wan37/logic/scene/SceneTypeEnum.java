package com.wan37.logic.scene;

/**
 * 场景类型枚举
 *
 * @author linda
 */
public enum SceneTypeEnum {

    /**
     * 普通场景
     */
    ORDINARY(1),

    /**
     * 副本场景
     */
    DUNGEON(2),

    /**
     * 竞技场场景
     */
    ARENA(3);

    private Integer id;

    SceneTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
