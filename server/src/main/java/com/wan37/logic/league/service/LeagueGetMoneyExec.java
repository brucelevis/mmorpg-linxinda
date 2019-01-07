package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueGetMoneyExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    public void exec(Player player, Integer cfgId, long amount) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        ILeagueMember me = league.getMember(player.getUid());
        LeaguePositionCfg positionCfg = leaguePositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorException("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_4.getId())) {
            throw new GeneralErrorException("没有取帮会物品的权限");
        }

        ILWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            if (warehouse.queryCurrency(cfgId) < amount) {
                player.syncClient("公会仓库钱不足");
                return;
            }

            warehouse.rmCurrency(cfgId, amount);
            league.save();

            ResourceElement resourceElement = new ResourceElementImpl(cfgId, amount);
            resourceFacade.giveResource(resourceElement, player);

            currencyUpdateNotifier.notify(player);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            warehouse.unlock();
        }
    }
}
