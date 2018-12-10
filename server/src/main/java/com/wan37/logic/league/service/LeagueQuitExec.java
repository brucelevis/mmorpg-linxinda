package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.player.Player;
import com.wan37.util.NetTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeagueQuitExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private NetTool netTool;

    @Autowired
    private LeagueDao leagueDao;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你未加入公会");
        }

        LeagueGlobalDb leagueGlobalDb = leagueGlobalManager.getLeague(player.getLeagueUid());
        if (leagueGlobalDb == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        if (isCreator(leagueGlobalDb, player.getUid())) {
            throw new GeneralErrorExecption("不允许创建者退出公会");
        }

        String msg = String.format("【公会】 [%s]退出了公会", player.getName());
        netTool.send(msg, leagueGlobalDb.getMembers().stream()
                .map(LeagueMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));

        player.setLeagueUid(null);
        LeagueMemberDb memberDb = findMember(leagueGlobalDb, player.getUid());
        leagueGlobalDb.getMembers().remove(memberDb);
        leagueDao.save(leagueGlobalDb);
    }

    private boolean isCreator(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .anyMatch(m -> Objects.equals(m.getPosition(), LeaguePositionEnum.LEAGUE_POSITION_1.getId()));
    }

    private LeagueMemberDb findMember(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .findAny()
                .orElse(null);
    }
}
