package com.wan37.logic.guild.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.guild.GuildWarehouse;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildGetMoneyExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player, Integer cfgId, long amount) {
        if (player.getGuildUid() == null) {
            player.syncClient("未加入公会");
            return;
        }

        Guild guild = guildGlobalManager.get(player.getGuildUid());
        GuildMember me = guild.getMember(player.getUid());
        GuildPositionCfg positionCfg = configLoader.load(GuildPositionCfg.class, me.getPosition()).orElse(null);
        if (positionCfg == null) {
            player.syncClient("找不到公会权限表");
            return;
        }

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.GET_WAREHOUSE_ITEM.getId())) {
            player.syncClient("没有取帮会物品的权限");
            return;
        }

        GuildWarehouse warehouse = guild.getWarehouse();
        try {
            guild.lock();
            if (warehouse.queryCurrency(cfgId) < amount) {
                player.syncClient("公会仓库钱不足");
                return;
            }

            warehouse.rmCurrency(cfgId, amount);
            guild.save();

            ResourceElement resourceElement = new ResourceElementImpl(cfgId, amount);
            resourceFacade.giveResource(resourceElement, player);

            currencyUpdateNotifier.notify(player);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            guild.unlock();
        }
    }
}
