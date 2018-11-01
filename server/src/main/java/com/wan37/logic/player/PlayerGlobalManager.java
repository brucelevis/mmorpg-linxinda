package com.wan37.logic.player;

import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.init.PlayerCreator;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerGlobalManager {

    /**
     * @see Player#getUid
     */
    private static Map<Long, Player> playerMap = new ConcurrentHashMap<>();

    /**
     * key: Channel#id
     * value: Player#getUid
     */
    private static Map<String, Long> channelMap = new ConcurrentHashMap<>();

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerCreator playerCreator;

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
        Player player = playerMap.get(uid);
        if (player != null) {
            return player;
        }

        PlayerDb playerDb = playerDao.getByUid(uid);
        if (playerDb == null) {
            return null;
        }

        Player newPlayer = playerCreator.create(playerDb, channel);
        add(newPlayer);

        return newPlayer;
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
