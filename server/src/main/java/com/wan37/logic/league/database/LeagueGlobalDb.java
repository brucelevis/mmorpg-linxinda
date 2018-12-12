package com.wan37.logic.league.database;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_league")
public class LeagueGlobalDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String name;

    private long createTime;

    private int maxNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<LeagueMemberDb> members;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<LeagueItemDb> items;

    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "league_uid")
    private Set<LeagueCurrencyDb> currency;

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

    public Set<LeagueMemberDb> getMembers() {
        return members;
    }

    public void setMembers(Set<LeagueMemberDb> members) {
        this.members = members;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Set<LeagueItemDb> getItems() {
        return items;
    }

    public void setItems(Set<LeagueItemDb> items) {
        this.items = items;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<LeagueCurrencyDb> getCurrency() {
        return currency;
    }

    public void setCurrency(Set<LeagueCurrencyDb> currency) {
        this.currency = currency;
    }
}
