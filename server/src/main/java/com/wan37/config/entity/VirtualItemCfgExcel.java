package com.wan37.config.entity;

/**
 * 虚物（如钱，积分等）配置表实体类
 *
 * @author linda
 */
public class VirtualItemCfgExcel {

    private Integer id;
    private String name;
    private long maxOverlay;

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

    public long getMaxOverlay() {
        return maxOverlay;
    }

    public void setMaxOverlay(long maxOverlay) {
        this.maxOverlay = maxOverlay;
    }
}
