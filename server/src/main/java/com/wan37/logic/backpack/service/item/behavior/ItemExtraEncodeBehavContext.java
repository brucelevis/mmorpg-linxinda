package com.wan37.logic.backpack.service.item.behavior;

public class ItemExtraEncodeBehavContext {

    public ItemExtraEncodeBehavContext(Integer cfgId, Object extraDb) {
        this.cfgId = cfgId;
        this.extraDb = extraDb;
    }

    public Integer getCfgId() {
        return cfgId;
    }

    public Object getExtraDb() {
        return extraDb;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private final Integer cfgId;
    private final Object extraDb;
    private String result;
}
