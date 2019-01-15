package com.wan37.logic.team.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.Team;
import com.wan37.logic.team.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class TeamInfoExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        if (player.getTeamUid() == null) {
            player.syncClient("未加入组队");
            return;
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        String msg = encodeTeam(team);
        player.syncClient(msg);
    }

    private String encodeTeam(Team team) {
        String head = "组队信息如下：\n";
        String msg = team.getMembers().stream()
                .map(m -> encodeTeamMember(m, team.getLeaderUid()))
                .collect(Collectors.joining("\n"));

        return head + msg;
    }

    private String encodeTeamMember(TeamMember member, Long leaderUid) {
        Long playerUid = member.getPlayerUid();
        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        boolean isOnline = playerGlobalManager.isOnline(playerUid);

        return String.format("%s（playerUid：%s） %s lv%s %s（%s）", player.getName(), player.getUid(),
                configLoader.loadName(FactionCfg.class, player.getFactionId()), player.getLevel(),
                Objects.equals(leaderUid, playerUid) ? "队长" : "成员", isOnline ? "在线" : "离线");
    }
}
