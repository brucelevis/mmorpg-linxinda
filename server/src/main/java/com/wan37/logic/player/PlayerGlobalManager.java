package com.wan37.logic.player;

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

    public void add(Player player) {
        playerMap.putIfAbsent(player.getUid(), player);
        channelMap.putIfAbsent(player.getChannel().id().asLongText(), player.getUid());
    }

    public Optional<Player> selectPlayerByUid(Long uid) {
        if (playerMap.containsKey(uid)) {
            return Optional.of(playerMap.get(uid));
        }

        // TODO: 查找数据库

        return Optional.empty();
    }
}
