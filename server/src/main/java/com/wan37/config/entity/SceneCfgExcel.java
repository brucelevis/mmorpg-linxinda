package com.wan37.config.entity;

/**
 * 场景配置表实体类
 *
 * @author linda
 */
public class SceneCfgExcel {

    private Integer id;
    private String name;
    private boolean defaultScene;
    private String monsters;
    private String npcs;
    private boolean canAttack;

    /**
     * 分隔符|
     */
    private String neighbor;
    private Integer type;

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

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
