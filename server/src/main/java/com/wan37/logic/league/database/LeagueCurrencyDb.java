package com.wan37.logic.league.database;

import javax.persistence.*;

@Entity
@Table(name = "tb_league_currency")
public class LeagueCurrencyDb {

    @Id
    private Long id;

    private Integer cfgId;

    private long amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
