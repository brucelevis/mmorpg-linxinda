package com.wan37.logic.player;


import com.wan37.common.GeneralResponseDto;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.GeneralNotifySenderUtil;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Player implements IPlayer {

    private Channel channel;
    private PlayerDb playerDb;

    private List<IBuff> buffs = new CopyOnWriteArrayList<>();

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public PlayerDb getPlayerDb() {
        return playerDb;
    }

    public void setPlayerDb(PlayerDb playerDb) {
        this.playerDb = playerDb;
    }

    public List<IBuff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<IBuff> buffs) {
        this.buffs = buffs;
    }

    public IPlayer getIPlayer() {
        return this;
    }

    @Override
    public Long getUid() {
        return playerDb.getUid();
    }

    @Override
    public Integer getSceneId() {
        return playerDb.getSceneId();
    }

    @Override
    public void setSceneId(Integer sceneId) {
        playerDb.setSceneId(sceneId);
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
        return playerDb.getLevel();
    }

    @Override
    public void syncClient(GeneralResponseDto dto) {
        GeneralNotifySenderUtil.send(channel, dto);
    }

    @Override
    public void syncClient(String content) {
        GeneralNotifySenderUtil.send(channel, content);
    }
}
