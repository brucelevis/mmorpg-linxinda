package com.wan37.logic.guild.database;

import javax.persistence.*;
import java.util.Set;

/**
 * 公会数据库实例
 *
 * @author linda
 */
@Entity
@Table(name = "tb_guild")
public class GuildGlobalDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String name;

    private long createTime;

    private int maxNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<GuildMemberDb> members;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<GuildItemDb> items;

    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<GuildCurrencyDb> currency;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Set<GuildMemberDb> getMembers() {
        return members;
    }

    public void setMembers(Set<GuildMemberDb> members) {
        this.members = members;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Set<GuildItemDb> getItems() {
        return items;
    }

    public void setItems(Set<GuildItemDb> items) {
        this.items = items;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<GuildCurrencyDb> getCurrency() {
        return currency;
    }

    public void setCurrency(Set<GuildCurrencyDb> currency) {
        this.currency = currency;
    }
}
