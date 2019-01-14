package com.wan37.logic.guild.service.donate.money;

import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.database.GuildCurrencyDb;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildCurrency;
import com.wan37.logic.guild.entity.GuildWarehouse;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class LeagueDonateMoneyExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    @Autowired
    private GuildCurrency.Factory leagueCurrencyFactory;

    public void exec(Player player, Integer id, long amount) {
        if (player.getGuildUid() == null) {
            player.syncClient("未加入公会");
            return;
        }

        if (resourceFacade.queryCurrency(id, player) < amount) {
            player.syncClient("没有足够的钱捐献");
            return;
        }

        Guild guild = guildGlobalManager.get(player.getGuildUid());
        GuildWarehouse warehouse = guild.getWarehouse();
        try {
            guild.lock();
            ResourceElement resourceElement = new ResourceElementImpl(id, amount);
            resourceFacade.consumeResource(resourceElement, player);

            GuildCurrencyDb guildCurrencyDb = createCurrencyDb(id, amount);
            GuildCurrency leagueCurrency = leagueCurrencyFactory.create(guildCurrencyDb);
            warehouse.addCurrency(leagueCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            guild.unlock();
        }

        guild.save();
        currencyUpdateNotifier.notify(player);
    }

    private GuildCurrencyDb createCurrencyDb(Integer cfgId, long amount) {
        GuildCurrencyDb db = new GuildCurrencyDb();
        db.setId(IdUtil.generate());
        db.setCfgId(cfgId);
        db.setAmount(amount);
        return db;
    }
}
