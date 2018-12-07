package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.dao.LeagueMemberDao;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.database.LeagueRootDb;
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
    private LeagueMemberDao leagueMemberDao;

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    public void exec(Player player, String name) {
        if (leagueDao.existsByName(name)) {
            throw new GeneralErrorExecption("已经存在的公会名");
        }

        //TODO: 检查创建公会的条件如钱啊啥的

        LeagueRootDb rootDb = leagueDao.save(createLeague(name));
        player.setLeagueUid(rootDb.getUid());

        LeagueMemberDb memberDb = createMember(player.getUid());
        rootDb.addMember(memberDb);

        leagueMemberDao.save(memberDb);

        leagueGlobalManager.addLeague(rootDb);
    }

    private LeagueRootDb createLeague(String name) {
        LeagueRootDb rootDb = new LeagueRootDb();
        rootDb.setMaxNum(20);
        rootDb.setName(name);
        rootDb.setMembers(new HashSet<>());

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        rootDb.setCreateTime(now);

        return rootDb;
    }

    private LeagueMemberDb createMember(Long playerUid) {
        LeagueMemberDb memberDb = new LeagueMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setJob(1);
        return memberDb;
    }
}
