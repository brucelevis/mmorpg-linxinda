package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.player.PlayerGlobalManager;

class GuildMemberImpl implements GuildMember {

    public GuildMemberImpl(GuildMemberDb guildMemberDb, PlayerGlobalManager playerGlobalManager) {
        this.guildMemberDb = guildMemberDb;
        this.playerGlobalManager = playerGlobalManager;
    }

    @Override
    public GuildMemberDb getGuildMemberDb() {
        return guildMemberDb;
    }

    @Override
    public Long getPlayerUid() {
        return guildMemberDb.getPlayerUid();
    }

    @Override
    public Integer getPosition() {
        return guildMemberDb.getPosition();
    }

    @Override
    public void setPosition(Integer position) {
        guildMemberDb.setPosition(position);
    }

    @Override
    public String getName() {
        return playerGlobalManager.getPlayerByUid(guildMemberDb.getPlayerUid()).getName();
    }

    private final GuildMemberDb guildMemberDb;
    private final PlayerGlobalManager playerGlobalManager;
}
