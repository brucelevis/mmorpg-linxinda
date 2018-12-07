package com.wan37.logic.league.database;

import javax.persistence.*;

@Entity
@Table(name = "tb_league_member")
public class LeagueMemberDb {

    @Id
    private Long playerUid;

    private Integer job;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueUid")
    private LeagueRootDb league;

    public Long getPlayerUid() {
        return playerUid;
    }

    public void setPlayerUid(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public LeagueRootDb getLeague() {
        return league;
    }

    public void setLeague(LeagueRootDb league) {
        this.league = league;
    }
}
