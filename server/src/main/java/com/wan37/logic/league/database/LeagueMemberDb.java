package com.wan37.logic.league.database;

import javax.persistence.*;

@Entity
@Table(name = "tb_league_member")
public class LeagueMemberDb {

    @Id
    private Long playerUid;

    private Integer position;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueUid")
    private LeagueGlobalDb league;

    public Long getPlayerUid() {
        return playerUid;
    }

    public void setPlayerUid(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LeagueGlobalDb getLeague() {
        return league;
    }

    public void setLeague(LeagueGlobalDb league) {
        this.league = league;
    }
}
