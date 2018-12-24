package com.wan37.config.entity;

public class MissionCfgExcel {

    private Integer id;
    private int level;
    private Integer preId;
    private Integer nextId;
    private String name;
    private String desc;
    private String detail;
    private String completeTip;
    private String proceedTip;
    private String reward;
    private long exp;
    private Integer npcId;
    private Integer sceneId;
    private Integer type;
    private Integer targetId;
    private int args;
    private boolean autoAccept;
    private boolean autoCommit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getPreId() {
        return preId;
    }

    public void setPreId(Integer preId) {
        this.preId = preId;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCompleteTip() {
        return completeTip;
    }

    public void setCompleteTip(String completeTip) {
        this.completeTip = completeTip;
    }

    public String getProceedTip() {
        return proceedTip;
    }

    public void setProceedTip(String proceedTip) {
        this.proceedTip = proceedTip;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public Integer getNpcId() {
        return npcId;
    }

    public void setNpcId(Integer npcId) {
        this.npcId = npcId;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public int getArgs() {
        return args;
    }

    public void setArgs(int args) {
        this.args = args;
    }

    public boolean isAutoAccept() {
        return autoAccept;
    }

    public void setAutoAccept(boolean autoAccept) {
        this.autoAccept = autoAccept;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
}
