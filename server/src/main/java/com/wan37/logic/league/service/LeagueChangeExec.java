package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.dao.LeagueMemberDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.player.Player;
import com.wan37.util.NetTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeagueChangeExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    @Autowired
    private NetTool netTool;

    @Autowired
    private LeagueMemberDao leagueMemberDao;

    public void exec(Player player, Player target) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你未加入公会");
        }

        LeagueGlobalDb leagueGlobalDb = leagueGlobalManager.getLeague(player.getLeagueUid());
        if (leagueGlobalDb == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        Integer myPosition = getPosition(leagueGlobalDb, player.getUid());
        LeaguePositionCfg myPositionCfg = leaguePositionCfgLoader.load(myPosition)
                .orElseThrow(() -> new GeneralErrorExecption("找不到公会权限表"));

        if (!myPositionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_3.getId())) {
            throw new GeneralErrorExecption("你的职位不能改变公会成员的权限");
        }

        LeagueMemberDb memberDb = findMember(leagueGlobalDb, target.getUid());
        if (memberDb == null) {
            throw new GeneralErrorExecption("目标不是该公会成员");
        }

        Integer targetPosition = getPosition(leagueGlobalDb, target.getUid());
        if (myPosition >= targetPosition) {
            throw new GeneralErrorExecption("不能修改同职级或更高职级的人");
        }

        LeaguePositionCfg newPositionCfg = getNewPositionCfg(myPosition, targetPosition);
        memberDb.setPosition(newPositionCfg.getId());
        leagueMemberDao.save(memberDb);

        // 广播
        String msg = String.format("【公会】 [%s]将[%s]的职位调整为[%s]", player.getName(), target.getName(), newPositionCfg.getName());
        netTool.send(msg, leagueGlobalDb.getMembers().stream()
                .map(LeagueMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));
    }

    private LeaguePositionCfg getNewPositionCfg(Integer myPosition, Integer targetPosition) {
        if (myPosition == targetPosition - 1) {
            return leaguePositionCfgLoader.loads().stream()
                    .max(Comparator.comparingInt(LeaguePositionCfg::getId))
                    .orElseThrow(() -> new GeneralErrorExecption("公会职位配置表出错"));
        }

        return leaguePositionCfgLoader.load(targetPosition - 1)
                .orElseThrow(() -> new GeneralErrorExecption("公会职位配置表出错"));
    }

    private Integer getPosition(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .findAny()
                .map(LeagueMemberDb::getPosition)
                .orElseThrow(() -> new GeneralErrorExecption("公会成员权限异常"));
    }

    private LeagueMemberDb findMember(LeagueGlobalDb leagueGlobalDb, Long playerUid) {
        return leagueGlobalDb.getMembers().stream()
                .filter(m -> Objects.equals(m.getPlayerUid(), playerUid))
                .findAny()
                .orElse(null);
    }
}
