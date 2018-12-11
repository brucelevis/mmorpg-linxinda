package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueKickExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

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

        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_2.getId())) {
            throw new GeneralErrorExecption("没有踢除公会成员的权限");
        }

        ILeagueMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            throw new GeneralErrorExecption("目标不是该公会成员");
        }

        if (me.getPosition() >= targetMember.getPosition()) {
            throw new GeneralErrorExecption("不能踢除同职级或更高职级的人");
        }

        // 广播
        String msg = String.format("【公会】 [%s]踢除了[%s]", player.getName(), target.getName());
        league.notifyAll(msg);

        target.setLeagueUid(null);
        league.rmMember(target.getUid());
        league.save();
    }
}
