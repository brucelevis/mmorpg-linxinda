package com.wan37.logic.guild;

/**
 * 公会职位枚举
 *
 * @author linda
 */
public enum GuildPositionEnum {

    /**
     * 会长
     */
    GUILD_POSITION_1(1, "会长"),

    /**
     * 副会长
     */
    GUILD_POSITION_2(2, "副会长"),

    /**
     * 精英
     */
    GUILD_POSITION_3(3, "精英"),

    /**
     * 普通成员
     */
    GUILD_POSITION_4(4, "普通成员");

    private Integer id;
    private String name;

    GuildPositionEnum(Integer id, String name) {
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

    public static String getName(int id) {
        for (GuildPositionEnum position : GuildPositionEnum.values()) {
            if (position.getId() == id) {
                return position.name;
            }
        }
        return "";
    }
}
