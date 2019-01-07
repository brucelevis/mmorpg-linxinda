package com.wan37.config.entity;

/**
 * 副本配置表实体类
 *
 * @author linda
 */
public class DungeonCfgExcel {

    private Integer id;
    private String name;
    private String monsters;
    private int limitTime;
    private String reward;
    private String desc;
    private Integer sceneId;
    private String completeTip;
    private int limitNum;

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

    public String getMonsters() {
        return monsters;
    }

    public void setMonsters(String monsters) {
        this.monsters = monsters;
    }

    public int getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public String getCompleteTip() {
        return completeTip;
    }

    public void setCompleteTip(String completeTip) {
        this.completeTip = completeTip;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
