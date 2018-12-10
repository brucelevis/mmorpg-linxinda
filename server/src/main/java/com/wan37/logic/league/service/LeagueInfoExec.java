package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LeagueInfoExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你当前没有公会信息");
        }

        LeagueGlobalDb leagueGlobalDb = leagueGlobalManager.getLeague(player.getLeagueUid());
        if (leagueGlobalDb == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        String msg = String.format("公会名：%s  人数：%s/%s\n", leagueGlobalDb.getName(),
                leagueGlobalDb.getMembers().size(), leagueGlobalDb.getMaxNum());

        String members = leagueGlobalDb.getMembers().stream()
                .map(this::encodeMember)
                .collect(Collectors.joining("\n"));

        player.syncClient(msg + members);
    }

    @Deprecated
    private String encodeMember(LeagueMemberDb memberDb) {
        //FIXME: 直接load了Player，需要优化
        Long uid = memberDb.getPlayerUid();
        Player player = playerGlobalManager.getPlayerByUid(uid);
        if (player == null) {
            return "";
        }

        return String.format("%s（playerUid：%s） 职位：%s （%s）", player.getName(), uid,
                LeaguePositionEnum.getName(memberDb.getPosition()), playerGlobalManager.isOnline(uid) ? "在线" : "离线");
    }
}
