package com.wan37.logic.player;


import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;

class PlayerImpl implements Player {

    public PlayerImpl(PlayerDb playerDb, Channel channel) {
        this.playerDb = playerDb;
        this.channel = channel;
    }

    @Override
    public Long getUid() {
        return playerDb.getUid();
    }

    @Override
    public String getName() {
        return playerDb.getName();
    }

    @Override
    public Integer getFactionId() {
        return playerDb.getFactionId();
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public Integer getSceneId() {
        return playerDb.getSceneId();
    }

    private final PlayerDb playerDb;
    private final Channel channel;
}
