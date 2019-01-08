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
    GUILD_PERMISSION_1(1, "加人"),

    /**
     * 踢人
     */
    GUILD_PERMISSION_2(2, "踢人"),

    /**
     * 修改权限
     */
    GUILD_PERMISSION_3(3, "修改权限"),

    /**
     * 取帮会仓库
     */
    GUILD_PERMISSION_4(4, "取帮会仓库");

    private Integer id;
    private String name;

    GuildPermissionEnum(Integer id, String name) {
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
