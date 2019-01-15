package com.wan37.logic.guild;

import com.wan37.logic.guild.dao.GuildDao;
import com.wan37.logic.guild.database.GuildGlobalDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linda
 */
@Service
public class GuildGlobalManager {

    /**
     * key：guildUid
     */
    private static final Map<Long, Guild> GUILD_MAP = new ConcurrentHashMap<>();

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private Guild.Factory leagueFactory;

    public void addLeague(Guild league) {
        if (league == null) {
            return;
        }

        GUILD_MAP.put(league.getUid(), league);
    }

    public Guild get(Long uid) {
        if (!GUILD_MAP.containsKey(uid)) {
            GuildGlobalDb guildGlobalDb = guildDao.findByUid(uid);
            Guild league = leagueFactory.create(guildGlobalDb);
            addLeague(league);
        }

        return GUILD_MAP.get(uid);
    }

    public void remove(Long uid) {
        GUILD_MAP.remove(uid);
    }
}
