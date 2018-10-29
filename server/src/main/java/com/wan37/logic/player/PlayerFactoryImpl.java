package com.wan37.logic.player;

import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;


@Service
class PlayerFactoryImpl implements Player.Factory {

    @Override
    public Player create(PlayerDb playerDb, Channel channel) {
        if (playerDb == null || channel == null) {
            return null;
        }

        return new PlayerImpl(playerDb, channel);
    }
}
