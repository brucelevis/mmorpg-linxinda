package com.wan37.logic.player;


import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;

public interface Player {

    interface Factory {

        Player create(PlayerDb playerDb, Channel channel);
    }

    Long getUid();

    String getName();

    Integer getFactionId();

    int getLevel();

    Channel getChannel();

    Integer getSceneId();

    void setSceneId(Integer sceneId);

    void save();
}
