package com.wan37.logic.player.init;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class PlayerCreator {

    public Player create(PlayerDb playerDb, Channel channel) {
        Player player = new Player();
        player.setPlayerDb(playerDb);
        player.setChannel(channel);
        return player;
    }
}
