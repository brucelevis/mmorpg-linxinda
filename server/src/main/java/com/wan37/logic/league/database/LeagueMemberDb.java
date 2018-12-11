package com.wan37.logic.league.database;

import javax.persistence.*;

@Entity
@Table(name = "tb_league_member")
public class LeagueMemberDb {

    @Id
    private Long playerUid;

    private Integer position;

    @Column(name = "league_uid")
    private Long leagueUid;

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

    public Long getLeagueUid() {
        return leagueUid;
    }

    public void setLeagueUid(Long leagueUid) {
        this.leagueUid = leagueUid;
    }
}
