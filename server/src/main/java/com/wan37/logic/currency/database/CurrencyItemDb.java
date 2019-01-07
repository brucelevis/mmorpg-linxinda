package com.wan37.logic.currency.database;

import com.wan37.logic.props.config.VirtualItemCfg;

/**
 * 玩家的一种虚物
 *
 * @author linda
 */
public class CurrencyItemDb {

    /**
     * @see VirtualItemCfg#getId
     */
    private Integer id;

    private long amount;

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
}
