package com.wan37.logic.league.service;

import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.util.NetTool;
import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.dao.LeagueMemberDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeagueAddExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    @Autowired
    private LeagueMemberDao leagueMemberDao;

    @Autowired
    private NetTool netTool;

    public void exec(Player player, Player target) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你未加入公会");
        }

        LeagueGlobalDb leagueGlobalDb = leagueGlobalManager.getLeague(player.getLeagueUid());
        if (leagueGlobalDb == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        Integer myPosition = leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), player.getUid()))
                .findAny()
                .map(LeagueMemberDb::getPosition)
                .orElseThrow(() -> new GeneralErrorExecption("公会成员权限异常"));

        LeaguePositionCfg positionCfg = leaguePositionCfgLoader.load(myPosition)
                .orElseThrow(() -> new GeneralErrorExecption("找不到公会权限表"));
        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_1.getId())) {
            throw new GeneralErrorExecption("没有添加公会成员的权限");
        }

        if (target.getLeagueUid() != null) {
            throw new GeneralErrorExecption("目标已有公会");
        }

        // 添加进公会
        target.setLeagueUid(leagueGlobalDb.getUid());
        LeagueMemberDb memberDb = createMember(target.getUid());
        leagueGlobalDb.addMember(memberDb);

        leagueMemberDao.save(memberDb);

        // 公会广播
        String msg = String.format("【公会】 恭喜[%s]加入[%s]", target.getName(), leagueGlobalDb.getName());
        netTool.send(msg, leagueGlobalDb.getMembers().stream()
                .map(LeagueMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));
    }

    private LeagueMemberDb createMember(Long playerUid) {
        LeagueMemberDb memberDb = new LeagueMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setPosition(LeaguePositionEnum.LEAGUE_POSITION_4.getId());
        return memberDb;
    }
}
