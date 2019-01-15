package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildMemberFactoryImpl implements GuildMember.Factory {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public GuildMember create(GuildMemberDb guildMemberDb) {
        return new GuildMemberImpl(guildMemberDb, playerGlobalManager);
    }
}
