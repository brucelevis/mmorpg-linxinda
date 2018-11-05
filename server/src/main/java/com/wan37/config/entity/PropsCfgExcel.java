package com.wan37.config.entity;

public class PropsCfgExcel {

    private Integer id;

    private String name;

    private String desc;

    private int maxOverlay;

    private boolean canUse;

    private Integer useLogicId;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMaxOverlay() {
        return maxOverlay;
    }

    public void setMaxOverlay(int maxOverlay) {
        this.maxOverlay = maxOverlay;
    }

    public boolean isCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }

    public Integer getUseLogicId() {
        return useLogicId;
    }

    public void setUseLogicId(Integer useLogicId) {
        this.useLogicId = useLogicId;
    }
}
