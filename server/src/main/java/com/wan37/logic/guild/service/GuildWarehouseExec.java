package com.wan37.logic.guild.service;

import com.wan37.config.ConfigLoader;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.entity.GuildCurrency;
import com.wan37.logic.guild.entity.GuildItem;
import com.wan37.logic.guild.entity.GuildWarehouse;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class GuildWarehouseExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        GuildWarehouse warehouse = league.getWarehouse();
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

    private String encodeItem(GuildItem guildItem) {
        PropsCfg propsCfg = configLoader.load(PropsCfg.class, guildItem.getCfgId())

        String msg = "格子：%s，名字：%s，数量：%s";
        return String.format(msg, guildItem.getIndex(), propsCfgLoader.getName(guildItem.getCfgId()), guildItem.getAmount());
    }

    private String encodeCurrency(GuildCurrency leagueCurrency) {
        String msg = "%s：%s（cfgId：%s）";
        return String.format(msg, virtualItemCfgLoader.getName(leagueCurrency.getCfgId()), leagueCurrency.getAmount(), leagueCurrency.getCfgId());
    }
}
