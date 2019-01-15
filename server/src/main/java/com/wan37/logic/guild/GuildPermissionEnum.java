package com.wan37.logic.guild;

/**
 * 公会权限枚举
 *
 * @author linda
 */
public enum GuildPermissionEnum {

    /**
     * 加人
     */
    ADD_MEMBER(1),

    /**
     * 踢人
     */
    KICK_MEMBER(2),

    /**
     * 修改权限
     */
    CHANGE_PERMISSION(3),

    /**
     * 取帮会仓库
     */
    GET_WAREHOUSE_ITEM(4);

    private Integer id;

    GuildPermissionEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
