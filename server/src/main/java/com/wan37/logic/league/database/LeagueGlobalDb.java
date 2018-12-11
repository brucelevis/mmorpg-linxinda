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
}
