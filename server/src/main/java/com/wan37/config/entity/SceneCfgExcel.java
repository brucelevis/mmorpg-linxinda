package com.wan37.config.entity;

public class SceneCfgExcel {

    private Integer id;

    private String name;

    private boolean personal;

    private boolean defaultScene;

    private String monsters;

    private String npcs;

    /**
     * 分隔符|
     */
    private String neighbor;

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

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    public boolean isDefaultScene() {
        return defaultScene;
    }

    public void setDefaultScene(boolean defaultScene) {
        this.defaultScene = defaultScene;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getMonsters() {
        return monsters;
    }

    public void setMonsters(String monsters) {
        this.monsters = monsters;
    }

    public String getNpcs() {
        return npcs;
    }

    public void setNpcs(String npcs) {
        this.npcs = npcs;
    }
}
