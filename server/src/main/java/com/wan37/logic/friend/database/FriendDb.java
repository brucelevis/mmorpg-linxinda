package com.wan37.logic.friend.database;

import com.wan37.logic.friend.database.convert.FriendUidConverterImpl;
import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 玩家朋友数据数据库实体类
 *
 * @author linda
 */
@Entity
@Table(name = "tb_friend")
public class FriendDb implements Serializable {

    @Id
    private Long id;

    @Convert(converter = FriendUidConverterImpl.class)
    private Set<Long> friendUid;

    @OneToOne
    @JoinColumn(name = "player_uid", referencedColumnName = "uid", unique = true)
    private PlayerDb player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(Set<Long> friendUid) {
        this.friendUid = friendUid;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }
}
