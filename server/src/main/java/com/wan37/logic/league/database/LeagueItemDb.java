package com.wan37.logic.league.database;

import com.wan37.logic.league.database.convert.LItemDbConverterImpl;
import com.wan37.logic.props.config.PropsCfg;

import javax.persistence.*;

/**
 * 公会仓库格子
 */
@Entity
@Table(name = "tb_league_item")
public class LeagueItemDb {

    @Id
    private Long id;

    private Long itemUid;

    /**
     * @see PropsCfg#getId
     */
    private Integer cfgId;

    private Integer index_;

    /**
     * 存储该物品的额外信息
     */
    @Convert(converter = LItemDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private Object extraDb;

    private int amount;

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

    public Integer getIndex_() {
        return index_;
    }

    public void setIndex_(Integer index) {
        this.index_ = index;
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

    public Long getItemUid() {
        return itemUid;
    }

    public void setItemUid(Long itemUid) {
        this.itemUid = itemUid;
    }
}
