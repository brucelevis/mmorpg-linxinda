package com.wan37.logic.player;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.init.PlayerCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class PlayerGlobalManager {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerCreator playerCreator;

    /**
     * key: playerUid
     */
    private final LoadingCache<Long, Player> playerCache = CacheBuilder.newBuilder()
            .initialCapacity(10) // 设置缓存容器的初始容量大小为10 
            .maximumSize(100)  // 设置缓存容器的最大容量大小为100
            .recordStats() // 设置记录缓存命中率
            .concurrencyLevel(8) // 设置并发级别为8，智并发基本值可以同事些缓存的线程数
            .expireAfterAccess(60, TimeUnit.MINUTES)  // 设置过期时间
            .build(new CacheLoader<Long, Player>() {
                @Override
                public Player load(Long uid) {
                    PlayerDb playerDb = playerDao.getByUid(uid);
                    if (playerDb == null) {
                        return null;
                    }

                    return playerCreator.create(playerDb, null);
                }
            });

    /**
     * 所有在线玩家
     * <p>
     * key: Channel#id
     * value: Player#getUid
     */
    private static final Map<String, Long> channelMap = new ConcurrentHashMap<>();

    public void addInOnlineList(Player player) {
        channelMap.put(player.getChannel().id().asLongText(), player.getUid());
    }

    public void removeFromOnlineList(String channelId) {
        channelMap.remove(channelId);
    }

    public Player getPlayerByChannelId(String channelId) {
        Long playerUid = channelMap.get(channelId);
        if (playerUid == null) {
            return null;
        }
        return getPlayerByUid(playerUid);
    }

    public Player getPlayerByUid(Long uid) {
        return playerCache.getUnchecked(uid);
    }
}
