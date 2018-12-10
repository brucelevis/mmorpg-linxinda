package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
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
public class LeagueKickExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    @Autowired
    private LeagueDao leagueDao;

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

        Integer myPosition = getPosition(leagueGlobalDb, player.getUid());
        LeaguePositionCfg positionCfg = leaguePositionCfgLoader.load(myPosition)
                .orElseThrow(() -> new GeneralErrorExecption("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_2.getId())) {
            throw new GeneralErrorExecption("没有踢除公会成员的权限");
        }

        LeagueMemberDb memberDb = findMember(leagueGlobalDb, target.getUid());
        if (memberDb == null) {
            throw new GeneralErrorExecption("目标不是该公会成员");
        }

        Integer targetPosition = getPosition(leagueGlobalDb, target.getUid());
        if (myPosition >= targetPosition) {
            throw new GeneralErrorExecption("不能踢除同职级或更高职级的人");
        }

        // 广播
        String msg = String.format("【公会】 %s踢除了%s", player.getName(), target.getName());
        netTool.send(msg, leagueGlobalDb.getMembers().stream()
                .map(LeagueMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));

        target.setLeagueUid(null);
        leagueGlobalDb.getMembers().remove(memberDb);
        leagueDao.save(leagueGlobalDb);
    }

    private LeagueMemberDb findMember(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .findAny()
                .orElse(null);
    }

    private Integer getPosition(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .findAny()
                .map(LeagueMemberDb::getPosition)
                .orElseThrow(() -> new GeneralErrorExecption("公会成员权限异常"));
    }
}
