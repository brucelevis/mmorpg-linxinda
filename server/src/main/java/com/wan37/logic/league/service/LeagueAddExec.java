package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueAddExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    @Autowired
    private ILeagueMember.Factory leagueMemberFactory;

    public void exec(Player player, Player target) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        ILeagueMember me = league.getMember(player.getUid());
        LeaguePositionCfg positionCfg = leaguePositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorExecption("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_1.getId())) {
            throw new GeneralErrorExecption("没有添加公会成员的权限");
        }

        if (target.getLeagueUid() != null) {
            throw new GeneralErrorExecption("目标已有公会");
        }

        // 添加进公会
        LeagueMemberDb memberDb = createMember(target.getUid());
        ILeagueMember leagueMember = leagueMemberFactory.create(memberDb);

        target.setLeagueUid(league.getUid());
        league.addMember(leagueMember);
        league.save();

        // 公会广播
        String msg = String.format("【公会】 恭喜[%s]加入[%s]", target.getName(), league.getName());
        league.notifyAll(msg);
    }

    private LeagueMemberDb createMember(Long playerUid) {
        LeagueMemberDb memberDb = new LeagueMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setPosition(LeaguePositionEnum.LEAGUE_POSITION_4.getId());
        return memberDb;
    }
}
