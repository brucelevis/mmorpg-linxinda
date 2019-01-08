package com.wan37.config.entity;

/**
 * 公会职位配置表实体类
 *
 * @author linda
 */
public class GuildPositionCfgExcel {

    private Integer id;
    private String name;
    private String permission;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
