package com.wan37.logic.league;

public enum LeaguePermissionEnum {

    LeaguePermissionEnum_1(1, "加人"),
    LeaguePermissionEnum_2(2, "踢人"),
    LeaguePermissionEnum_3(3, "修改权限"),
    LeaguePermissionEnum_4(4, "取帮会仓库");

    private Integer id;
    private String name;

    LeaguePermissionEnum(Integer id, String name) {
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
