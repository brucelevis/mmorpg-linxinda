package com.wan37.logic.guild.database;

import com.wan37.logic.guild.GuildPositionEnum;

import javax.persistence.*;

/**
 * 公会成员数据库实例
 *
 * @author linda
 */
@Entity
@Table(name = "tb_guild_member")
public class GuildMemberDb {

    @Id
    private Long playerUid;

    /**
     * @see GuildPositionEnum#getId()
     */
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
