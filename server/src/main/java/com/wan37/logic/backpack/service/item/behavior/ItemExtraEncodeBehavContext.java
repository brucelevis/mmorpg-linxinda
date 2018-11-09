package com.wan37.logic.backpack.service.item.behavior;

public class ItemExtraEncodeBehavContext {

    public ItemExtraEncodeBehavContext(Object extraDb) {
        this.extraDb = extraDb;
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

    private final Object extraDb;
    private String result;
}
