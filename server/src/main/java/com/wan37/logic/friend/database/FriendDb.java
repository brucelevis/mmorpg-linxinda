package com.wan37.logic.friend.database;

import com.wan37.logic.friend.database.convert.FriendUidsConverterImpl;
import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
public class FriendDb implements Serializable {

    @Id
    private Long id;

    @Convert(converter = FriendUidsConverterImpl.class)
    private Set<Long> friendUids;

    @OneToOne
    @JoinColumn(name = "player_uid", referencedColumnName = "uid", unique = true)
    private PlayerDb player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getFriendUids() {
        return friendUids;
    }

    public void setFriendUids(Set<Long> friendUids) {
        this.friendUids = friendUids;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }
}
