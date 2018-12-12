package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.entity.ILeagueCurrency;
import com.wan37.logic.league.entity.ILeagueItem;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LeagueWarehouseExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        ILWarehouse warehouse = league.getWarehouse();
        String itemHead = String.format("公会仓库总容量：%s，当前容量：%s，物品信息如下：\n", warehouse.getCapacity(), warehouse.getCurSize());
        String items = warehouse.getItems().stream()
                .map(this::encodeItem)
                .collect(Collectors.joining("\n"));

        String currencyHead = "\n虚物信息如下：\n";
        String currency = warehouse.getCurrency().stream()
                .map(this::encodeCurrency)
                .collect(Collectors.joining("n"));

        player.syncClient(itemHead + items + currencyHead + currency);
    }

    private String encodeItem(ILeagueItem ILeagueItem) {
        String msg = "格子：%s，名字：%s，数量：%s";
        return String.format(msg, ILeagueItem.getIndex(), propsCfgLoader.getName(ILeagueItem.getCfgId()), ILeagueItem.getAmount());
    }

    private String encodeCurrency(ILeagueCurrency leagueCurrency) {
        String msg = "%s：%s（cfgId：%s）";
        return String.format(msg, virtualItemCfgLoader.getName(leagueCurrency.getCfgId()), leagueCurrency.getAmount(), leagueCurrency.getCfgId());
    }
}
