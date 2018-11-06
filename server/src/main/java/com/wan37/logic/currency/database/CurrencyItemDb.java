package com.wan37.logic.currency.database;

import com.wan37.logic.props.config.VirtualItemCfg;

public class CurrencyItemDb {

    /**
     * @see VirtualItemCfg#getId
     */
    private Integer id;

    private long amount;

    /**
     * FIXME: 为了encode给前端方便，存起来名字
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
