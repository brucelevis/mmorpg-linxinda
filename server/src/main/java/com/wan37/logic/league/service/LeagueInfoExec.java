package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
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

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        String msg = String.format("公会名：%s  人数：%s/%s\n", league.getName(), league.getCurNum(), league.getMaxNum());
        String members = league.getMembers().stream()
                .map(this::encodeMember)
                .collect(Collectors.joining("\n"));

        player.syncClient(msg + members);
    }

    private String encodeMember(ILeagueMember member) {
        return String.format("%s（playerUid：%s） 职位：%s （%s）", member.getName(), member.getPlayerUid(),
                LeaguePositionEnum.getName(member.getPosition()), playerGlobalManager.isOnline(member.getPlayerUid()) ? "在线" : "离线");
    }
}
