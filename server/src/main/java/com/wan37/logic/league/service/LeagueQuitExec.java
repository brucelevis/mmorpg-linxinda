package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LeagueQuitExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("你未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorException("公会不存在");
        }

        if (Objects.equals(league.getLeaderUid(), player.getUid())) {
            throw new GeneralErrorException("不允许创建者退出公会");
        }

        String msg = String.format("【公会】 [%s]退出了公会", player.getName());
        league.notifyAll(msg);

        player.setLeagueUid(null);
        league.rmMember(player.getUid());
        league.save();
    }
}
