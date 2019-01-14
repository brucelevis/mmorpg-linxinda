package com.wan37.logic.team.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class TeamInviteExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player, Player target) {
        if (player.getTeamUid() == null) {
            player.syncClient("你未创建组队");
            return;
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
            player.syncClient("你不是队长，无法发出组队邀请");
            return;
        }

        if (target.getTeamUid() != null) {
            player.syncClient("对方已有队伍");
            return;
        }

        if (team.getMember(target.getUid()) != null) {
            player.syncClient("已经是队伍成员");
            return;
        }

        player.syncClient(String.format("你对[%s]发起了组队邀请", target.getName()));
        target.syncClient(String.format("[%s]向你发起了组队邀请（teamUid：%s）", player.getName(), team.getUid()));
    }
}
