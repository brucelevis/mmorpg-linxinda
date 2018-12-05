package com.wan37.logic.friend.database;

import com.wan37.logic.friend.database.convert.FriendRequestDbConverterImpl;
import com.wan37.logic.friend.database.convert.FriendUidsConverterImpl;
import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class FriendDb {

    @Id
    private Long playerUid;

    @Convert(converter = FriendUidsConverterImpl.class)
    private Set<Long> friendUids;

    @Convert(converter = FriendRequestDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private FriendRequestDb friendRequestDb;

    @OneToOne
    @JoinColumn(name = "player_uid", referencedColumnName = "uid", unique = true)
    private PlayerDb player;

    public Long getPlayerUid() {
        return playerUid;
    }

    public void setPlayerUid(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Set<Long> getFriendUids() {
        return friendUids;
    }

    public void setFriendUids(Set<Long> friendUids) {
        this.friendUids = friendUids;
    }

    public FriendRequestDb getFriendRequestDb() {
        return friendRequestDb;
    }

    public void setFriendRequestDb(FriendRequestDb friendRequestDb) {
        this.friendRequestDb = friendRequestDb;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }
}
