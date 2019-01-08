package com.wan37.logic.guild.service.donate.money;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.database.GuildCurrencyDb;
import com.wan37.logic.guild.entity.GuildWarehouse;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildCurrency;
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
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        if (resourceFacade.queryCurrency(id, player) < amount) {
            throw new GeneralErrorException("没有足够的钱捐献");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        GuildWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            ResourceElement resourceElement = new ResourceElementImpl(id, amount);
            resourceFacade.consumeResource(resourceElement, player);

            GuildCurrencyDb guildCurrencyDb = createCurrencyDb(id, amount);
            GuildCurrency leagueCurrency = leagueCurrencyFactory.create(guildCurrencyDb);
            warehouse.addCurrency(leagueCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            warehouse.unlock();
        }

        league.save();
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
