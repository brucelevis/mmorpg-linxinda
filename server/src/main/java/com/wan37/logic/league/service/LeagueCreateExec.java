package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;


@Service
public class LeagueCreateExec {

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private ILeague.Factory leagueFactory;

    @Autowired
    private ILeagueMember.Factory leagueMemberFactory;

    public void exec(Player player, String name) {
        if (leagueDao.existsByName(name)) {
            throw new GeneralErrorExecption("已经存在的公会名");
        }

        //TODO: 检查创建公会的条件如钱啊啥的

        if (player.getLeagueUid() != null) {
            throw new GeneralErrorExecption("已有公会，要创建公会需要退出或解散当前公会");
        }

        // 创建公会
        LeagueGlobalDb leagueGlobalDb = leagueDao.save(createLeague(name));
        ILeague league = leagueFactory.create(leagueGlobalDb);
        leagueGlobalManager.addLeague(league);

        // 创建成员
        LeagueMemberDb memberDb = createMember(player.getUid(), league.getUid());
        ILeagueMember leagueMember = leagueMemberFactory.create(memberDb);

        league.addMember(leagueMember);
        league.save();

        player.setLeagueUid(league.getUid());

        chatFacade.chatToWorld(String.format("【公告】 祝贺[%s]创建了[%s]公会", player.getName(), name));
    }

    private LeagueGlobalDb createLeague(String name) {
        LeagueGlobalDb rootDb = new LeagueGlobalDb();
        rootDb.setMaxNum(20);
        rootDb.setName(name);
        rootDb.setMembers(new HashSet<>());

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        rootDb.setCreateTime(now);

        return rootDb;
    }

    private LeagueMemberDb createMember(Long playerUid, Long leagueUid) {
        LeagueMemberDb memberDb = new LeagueMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setLeagueUid(leagueUid);
        memberDb.setPosition(LeaguePositionEnum.LEAGUE_POSITION_1.getId());
        return memberDb;
    }
}
