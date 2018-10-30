package com.wan37.logic.player;


import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;

class PlayerImpl implements Player {

    public PlayerImpl(PlayerDb playerDb, Channel channel, PlayerDao playerDao) {
        this.playerDb = playerDb;
        this.channel = channel;
        this.playerDao = playerDao;
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

    @Override
    public void setSceneId(Integer sceneId) {
        playerDb.setSceneId(sceneId);
    }

    @Override
    public void save() {
        playerDao.save(playerDb);
    }

    private final PlayerDb playerDb;
    private final Channel channel;

    private final PlayerDao playerDao;
}
