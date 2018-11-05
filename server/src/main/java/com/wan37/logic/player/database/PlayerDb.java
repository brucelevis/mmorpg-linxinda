package com.wan37.logic.player.database;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.convert.BackpackDbConverterImpl;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private BackpackDb backpackDb;

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
}
