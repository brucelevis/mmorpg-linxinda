package com.wan37.logic.guild.database;

import com.wan37.logic.props.config.VirtualItemCfg;

import javax.persistence.*;

/**
 * 公会仓库一种虚物数据库实例
 *
 * @author linda
 */
@Entity
@Table(name = "tb_guild_currency")
public class GuildCurrencyDb {

    @Id
    private Long id;

    /**
     * @see VirtualItemCfg#getId()
     */
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
