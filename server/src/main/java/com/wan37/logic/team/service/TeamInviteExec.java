package com.wan37.logic.team.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeamInviteExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player, Player target) {
        if (player.getTeamUid() == null) {
            throw new GeneralErrorException("你未创建组队");
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
            throw new GeneralErrorException("你不是队长，无法发出组队邀请");
        }

        if (target.getTeamUid() != null) {
            throw new GeneralErrorException("对方已有队伍");
        }

        if (team.getMember(target.getUid()) != null) {
            throw new GeneralErrorException("已经是队伍成员");
        }

        player.syncClient(String.format("你对[%s]发起了组队邀请", target.getName()));
        target.syncClient(String.format("[%s]向你发起了组队邀请（teamUid：%s）", player.getName(), team.getUid()));
    }
}
