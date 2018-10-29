package com.wan37.logic.player;

import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PlayerGlobalManager {

    /**
     * @see Player#getUid
     */
    private static ConcurrentMap<Long, Player> playerMap = new ConcurrentHashMap<>();

    /**
     * key: Channel#id
     * value: Player#getUid
     */
    private static ConcurrentMap<String, Long> channelMap = new ConcurrentHashMap<>();

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private Player.Factory factory;

    public void add(Player player) {
        playerMap.putIfAbsent(player.getUid(), player);
        channelMap.putIfAbsent(player.getChannel().id().asLongText(), player.getUid());
    }

    public Player getPlayerByUid(Long uid, Channel channel) {
        if (playerMap.containsKey(uid)) {
            return playerMap.get(uid);
        }

        PlayerDb playerDb = playerDao.getByUid(uid);
        Player player = factory.create(playerDb, channel);
        add(player);

        return player;
    }
}
