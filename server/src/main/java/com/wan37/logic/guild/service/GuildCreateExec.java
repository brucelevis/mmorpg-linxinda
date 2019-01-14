package com.wan37.logic.guild.service;

import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.dao.GuildDao;
import com.wan37.logic.guild.database.GuildGlobalDb;
import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildMember;
import com.wan37.logic.player.Player;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @author linda
 */
@Service
public class GuildCreateExec {

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private Guild.Factory leagueFactory;

    @Autowired
    private GuildMember.Factory leagueMemberFactory;

    public void exec(Player player, String name) {
        if (guildDao.existsByName(name)) {
            player.syncClient("已经存在的公会名");
            return;
        }

        //TODO: 检查创建公会的条件如钱啊啥的

        if (player.getGuildUid() != null) {
            player.syncClient("已有公会，要创建公会需要退出或解散当前公会");
            return;
        }

        // 创建公会
        GuildGlobalDb guildGlobalDb = guildDao.save(createLeague(name));
        Guild league = leagueFactory.create(guildGlobalDb);
        guildGlobalManager.addLeague(league);

        // 创建成员
        GuildMemberDb memberDb = createMember(player.getUid(), league.getUid());
        GuildMember leagueMember = leagueMemberFactory.create(memberDb);

        league.addMember(leagueMember);
        league.save();

        player.setGuildUid(league.getUid());

        chatFacade.chatToWorld(String.format("【公告】 祝贺[%s]创建了[%s]公会", player.getName(), name));
    }

    private GuildGlobalDb createLeague(String name) {
        GuildGlobalDb guildGlobalDb = new GuildGlobalDb();
        guildGlobalDb.setMaxNum(20);
        guildGlobalDb.setName(name);
        guildGlobalDb.setMembers(new HashSet<>());
        guildGlobalDb.setCapacity(10);
        guildGlobalDb.setItems(new HashSet<>());
        guildGlobalDb.setCurrency(new HashSet<>());

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        guildGlobalDb.setCreateTime(now);

        return guildGlobalDb;
    }

    private GuildMemberDb createMember(Long playerUid, Long leagueUid) {
        GuildMemberDb memberDb = new GuildMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setLeagueUid(leagueUid);
        memberDb.setPosition(GuildPositionEnum.GUILD_POSITION_1.getId());
        return memberDb;
    }
}
