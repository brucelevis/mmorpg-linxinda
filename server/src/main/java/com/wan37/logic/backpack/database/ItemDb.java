package com.wan37.logic.backpack.database;

import com.wan37.logic.props.config.PropsCfg;

public class ItemDb {

    private Long uid;

    /**
     * @see PropsCfg#getId
     */
    private Integer cfgId;

    /**
     * 背包位置
     */
    private Integer index;

    /**
     * 存储该物品的额外信息
     */
    private Object extraDb;

    private int amount;

    //FIXME: 为了返回的Json能打印知道这个东西是什么名字才存起来而已
    private String name;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Object getExtraDb() {
        return extraDb;
    }

    public void setExtraDb(Object extraDb) {
        this.extraDb = extraDb;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
