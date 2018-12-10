package com.wan37.logic.friend.database;

import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;

@Entity
@Table(name = "tb_friend_request")
public class FriendRequestDb {

    @Id
    private Long id;

    private long time;

    private Long fromPlayerUid;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerUid")
    private PlayerDb player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }

    public Long getFromPlayerUid() {
        return fromPlayerUid;
    }

    public void setFromPlayerUid(Long fromPlayerUid) {
        this.fromPlayerUid = fromPlayerUid;
    }
}
