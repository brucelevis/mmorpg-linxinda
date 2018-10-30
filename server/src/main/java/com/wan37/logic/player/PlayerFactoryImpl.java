package com.wan37.logic.player;

import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class PlayerFactoryImpl implements Player.Factory {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public Player create(PlayerDb playerDb, Channel channel) {
        if (playerDb == null || channel == null) {
            return null;
        }

        return new PlayerImpl(playerDb, channel, playerDao);
    }
}
