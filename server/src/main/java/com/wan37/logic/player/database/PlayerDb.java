package com.wan37.logic.player.database;

import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.database.convert.PlayerAttrDbConvertImpl;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.convert.BackpackDbConverterImpl;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.convert.CurrencyDbConvertImpl;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.convert.EquipDbConverterImpl;
import com.wan37.logic.mail.database.MailDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import com.wan37.logic.skill.database.convert.PlayerSkillDbConverterImpl;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.database.convert.PlayerStrengthDbConverterImpl;

import javax.persistence.*;
import java.util.List;

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

    /**
     * 血量
     */
    private long hp;

    /**
     * 蓝量
     */
    private long mp;

    /**
     * 经验
     */
    private double exp;

    @Convert(converter = BackpackDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private BackpackDb backpackDb;

    @Convert(converter = CurrencyDbConvertImpl.class)
    @Column(columnDefinition = "text")
    private CurrencyDb currencyDb;

    @Convert(converter = PlayerAttrDbConvertImpl.class)
    @Column(columnDefinition = "text")
    private PlayerAttrDb playerAttrDb;

    @Convert(converter = EquipDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private EquipDb equipDb;

    @Convert(converter = PlayerStrengthDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private PlayerStrengthDb playerStrengthDb;

    @Convert(converter = PlayerSkillDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private PlayerSkillDb playerSkillDb;

    /**
     * OneToMany必须写mappedBy，不然会多生成一张没用的中间表，因为如果没写mappedBy，JPA不知道具体怎样做关联。
     * orphanRemoval=true，PlayerDb删除邮件（即解除关系），持久化会删除MailDb对应的数据
     */
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<MailDb> mails;

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

    public EquipDb getEquipDb() {
        return equipDb;
    }

    public void setEquipDb(EquipDb equipDb) {
        this.equipDb = equipDb;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public PlayerStrengthDb getPlayerStrengthDb() {
        return playerStrengthDb;
    }

    public void setPlayerStrengthDb(PlayerStrengthDb playerStrengthDb) {
        this.playerStrengthDb = playerStrengthDb;
    }

    public PlayerSkillDb getPlayerSkillDb() {
        return playerSkillDb;
    }

    public void setPlayerSkillDb(PlayerSkillDb playerSkillDb) {
        this.playerSkillDb = playerSkillDb;
    }

    public List<MailDb> getMails() {
        return mails;
    }

    public void setMails(List<MailDb> mails) {
        this.mails = mails;
    }

    /**
     * 注意：往one一端的实体插入信息需要用关系维护端来维护关系
     */
    public void addMail(MailDb mailDb) {
        mailDb.setPlayer(this);
        this.mails.add(mailDb);
    }
}
