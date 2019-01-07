package com.wan37.logic.team.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeamDissolveExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        if (player.getTeamUid() == null) {
            throw new GeneralErrorException("你未创建组队");
        }

        ITeam team = teamGlobalManager.getTeam(player.getTeamUid());
        try {
            team.lock();

            if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
                player.syncClient("你不是队长，无法解散队伍");
                return;
            }

            // 移除在缓存中的玩家组队信息
            team.getMembers().stream()
                    .map(m -> playerGlobalManager.getPlayerIfPresent(m.getPlayerUid()))
                    .filter(Objects::nonNull)
                    .forEach(p -> p.setTeamUid(null));

            team.broadcast("队伍解散");
            teamGlobalManager.rmTeam(team.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            team.unLock();
        }
    }
}
