package com.wan37.logic.player.database;

import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.database.convert.PlayerAttrDbConvertImpl;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.convert.BackpackDbConverterImpl;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.convert.CurrencyDbConvertImpl;

import javax.persistence.*;

@Entity
@Table
public class PlayerDb {

    @Id
    private Long uid;

    /**
     * 玩家名
     */
    private String name;

    /**
     * 门派id
     */
    private Integer factionId;

    /**
     * 场景Id
     */
    private Integer sceneId;

    /**
     * 等级
     */
    private int level;

    @Convert(converter = BackpackDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private BackpackDb backpackDb;

    @Convert(converter = CurrencyDbConvertImpl.class)
    @Column(columnDefinition = "text")
    private CurrencyDb currencyDb;

    @Convert(converter = PlayerAttrDbConvertImpl.class)
    @Column(columnDefinition = "text")
    private PlayerAttrDb playerAttrDb;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFactionId() {
        return factionId;
    }

    public void setFactionId(Integer factionId) {
        this.factionId = factionId;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BackpackDb getBackpackDb() {
        return backpackDb;
    }

    public void setBackpackDb(BackpackDb backpackDb) {
        this.backpackDb = backpackDb;
    }

    public CurrencyDb getCurrencyDb() {
        return currencyDb;
    }

    public void setCurrencyDb(CurrencyDb currencyDb) {
        this.currencyDb = currencyDb;
    }

    public PlayerAttrDb getPlayerAttrDb() {
        return playerAttrDb;
    }

    public void setPlayerAttrDb(PlayerAttrDb playerAttrDb) {
        this.playerAttrDb = playerAttrDb;
    }
}
