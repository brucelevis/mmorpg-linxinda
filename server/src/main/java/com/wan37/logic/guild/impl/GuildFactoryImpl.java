package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.dao.GuildDao;
import com.wan37.logic.guild.database.GuildGlobalDb;
import com.wan37.logic.guild.GuildWarehouse;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.guild.GuildMember;
import com.wan37.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class GuildFactoryImpl implements Guild.Factory {

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private NetUtil netUtil;

    @Autowired
    private GuildMember.Factory leagueMemberFactory;

    @Autowired
    private GuildWarehouse.Factory warehouseFactory;

    @Override
    public Guild create(GuildGlobalDb guildGlobalDb) {
        if (guildGlobalDb == null) {
            return null;
        }

        Map<Long, GuildMember> memberMap = guildGlobalDb.getMembers().stream()
                .map(m -> leagueMemberFactory.create(m))
                .collect(Collectors.toMap(GuildMember::getPlayerUid, Function.identity()));

        GuildWarehouse warehouse = warehouseFactory.create(guildGlobalDb);
        return new GuildImpl(memberMap, guildGlobalDb, warehouse, guildDao, netUtil, new ReentrantLock());
    }
}
