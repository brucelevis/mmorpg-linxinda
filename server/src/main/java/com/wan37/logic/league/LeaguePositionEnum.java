package com.wan37.logic.league;

public enum LeaguePositionEnum {

    LEAGUE_POSITION_1(1, "会长"),
    LEAGUE_POSITION_2(2, "副会长"),
    LEAGUE_POSITION_3(3, "精英"),
    LEAGUE_POSITION_4(4, "普通成员");

    private Integer id;
    private String name;

    LeaguePositionEnum(Integer id, String name) {
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
