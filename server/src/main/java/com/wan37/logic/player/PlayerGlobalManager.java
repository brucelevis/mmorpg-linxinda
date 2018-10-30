package com.wan37.logic.player;

import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public void offLine(Channel channel) {
        String channelId = channel.id().asLongText();
        Long playerUid = channelMap.get(channelId);

        channelMap.remove(channelId);
        playerMap.remove(playerUid);
    }

    public Player getPlayerAndAddChannelByUid(Long uid, Channel channel) {
        if (playerMap.containsKey(uid)) {
            return playerMap.get(uid);
        }

        PlayerDb playerDb = playerDao.getByUid(uid);
        if (playerDb == null) {
            return null;
        }

        Player player = factory.create(playerDb, channel);
        add(player);

        return player;
    }

    public Optional<Player> findPlayerByUid(Long uid) {
        return Optional.of(playerMap.get(uid));
    }

    public Optional<Player> findPlayerByChannelId(String channelId) {
        if (!channelMap.containsKey(channelId)) {
            return Optional.empty();
        }

        Long playerUid = channelMap.get(channelId);
        return Optional.of(playerMap.get(playerUid));
    }
}
