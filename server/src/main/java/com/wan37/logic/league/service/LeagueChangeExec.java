package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class LeagueChangeExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    public void exec(Player player, Player target) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("你未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorException("公会不存在");
        }

        ILeagueMember me = league.getMember(player.getUid());
        LeaguePositionCfg myPositionCfg = leaguePositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorException("找不到公会权限表"));

        if (!myPositionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_3.getId())) {
            throw new GeneralErrorException("你的职位不能改变公会成员的权限");
        }

        ILeagueMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            throw new GeneralErrorException("目标不是该公会成员");

        }

        if (me.getPosition() >= targetMember.getPosition()) {
            throw new GeneralErrorException("不能修改同职级或更高职级的人");
        }

        LeaguePositionCfg newPositionCfg = getNewPositionCfg(me.getPosition(), targetMember.getPosition());
        targetMember.setPosition(newPositionCfg.getId());
        league.save();

        // 广播
        String msg = String.format("【公会】 [%s]将[%s]的职位调整为[%s]", player.getName(), target.getName(), newPositionCfg.getName());
        league.notifyAll(msg);
    }

    private LeaguePositionCfg getNewPositionCfg(Integer myPosition, Integer targetPosition) {
        if (myPosition == targetPosition - 1) {
            return leaguePositionCfgLoader.loads().stream()
                    .max(Comparator.comparingInt(LeaguePositionCfg::getId))
                    .orElseThrow(() -> new GeneralErrorException("公会职位配置表出错"));
        }

        return leaguePositionCfgLoader.load(targetPosition - 1)
                .orElseThrow(() -> new GeneralErrorException("公会职位配置表出错"));
    }
}
