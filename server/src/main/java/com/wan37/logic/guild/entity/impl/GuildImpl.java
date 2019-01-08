package com.wan37.logic.guild.entity.impl;

import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.dao.GuildDao;
import com.wan37.logic.guild.database.GuildGlobalDb;
import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.entity.GuildWarehouse;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildMember;
import com.wan37.util.NetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class GuildImpl implements Guild {

    public GuildImpl(Map<Long, GuildMember> memberMap, GuildGlobalDb guildGlobalDb, GuildWarehouse warehouse, GuildDao guildDao, NetUtil netUtil) {
        this.memberMap = memberMap;
        this.guildGlobalDb = guildGlobalDb;
        this.warehouse = warehouse;
        this.guildDao = guildDao;
        this.netUtil = netUtil;
    }

    @Override
    public Long getUid() {
        return guildGlobalDb.getUid();
    }

    @Override
    public String getName() {
        return guildGlobalDb.getName();
    }

    @Override
    public int getMaxNum() {
        return guildGlobalDb.getMaxNum();
    }

    @Override
    public int getCurNum() {
        return guildGlobalDb.getMembers().size();
    }

    @Override
    public List<GuildMember> getMembers() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public GuildMember getMember(Long playerUid) {
        return memberMap.get(playerUid);
    }

    @Override
    public void addMember(GuildMember member) {
        if (memberMap.containsKey(member.getPlayerUid())) {
            return;
        }
        memberMap.put(member.getPlayerUid(), member);
        guildGlobalDb.getMembers().add(member.getGuildMemberDb());
    }

    @Override
    public void rmMember(Long playerUid) {
        memberMap.remove(playerUid);

        guildGlobalDb.setMembers(guildGlobalDb.getMembers().stream()
                .filter(m -> !Objects.equals(m.getPlayerUid(), playerUid))
                .collect(Collectors.toSet()));
    }

    @Override
    public void notifyAll(String msg) {
        netUtil.send(msg, guildGlobalDb.getMembers().stream()
                .map(GuildMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));
    }

    @Override
    public void save() {
        guildDao.save(guildGlobalDb);
    }

    @Override
    public Long getLeaderUid() {
        return memberMap.values().stream()
                .filter(m -> Objects.equals(m.getPosition(), GuildPositionEnum.GUILD_POSITION_1.getId()))
                .map(GuildMember::getPlayerUid)
                .findAny()
                .orElseThrow(() -> new RuntimeException("公会找不到会长"));
    }

    @Override
    public GuildWarehouse getWarehouse() {
        return warehouse;
    }

    private final Map<Long, GuildMember> memberMap;
    private final GuildGlobalDb guildGlobalDb;
    private final GuildWarehouse warehouse;

    private final GuildDao guildDao;
    private final NetUtil netUtil;
}
