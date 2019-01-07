package com.wan37.logic.league.service.donate.money;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.database.LeagueCurrencyDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueCurrency;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueDonateMoneyExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    @Autowired
    private ILeagueCurrency.Factory leagueCurrencyFactory;

    public void exec(Player player, Integer id, long amount) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        if (resourceFacade.queryCurrency(id, player) < amount) {
            throw new GeneralErrorException("没有足够的钱捐献");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        ILWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            ResourceElement resourceElement = new ResourceElementImpl(id, amount);
            resourceFacade.consumeResource(resourceElement, player);

            LeagueCurrencyDb leagueCurrencyDb = createCurrencyDb(id, amount);
            ILeagueCurrency leagueCurrency = leagueCurrencyFactory.create(leagueCurrencyDb);
            warehouse.addCurrency(leagueCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            warehouse.unlock();
        }

        league.save();
        currencyUpdateNotifier.notify(player);
    }

    private LeagueCurrencyDb createCurrencyDb(Integer cfgId, long amount) {
        LeagueCurrencyDb db = new LeagueCurrencyDb();
        db.setId(IdUtil.generate());
        db.setCfgId(cfgId);
        db.setAmount(amount);
        return db;
    }
}
