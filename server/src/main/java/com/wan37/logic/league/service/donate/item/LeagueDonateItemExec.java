package com.wan37.logic.league.service.donate.item;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.database.LeagueItemDb;
import com.wan37.logic.league.entity.ILeagueItem;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.player.Player;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueDonateItemExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private ILeagueItem.Factory leagueItemFactory;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private IdTool idTool;

    public void exec(Player player, ReqLDonateItem reqLDonateItem) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        reqLDonateItem.getDonateItems().forEach(i -> checkBackpackItem(backpackDb, i.getIndex(), i.getAmount()));

        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        ILWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            if (reqLDonateItem.getDonateItems().size() > warehouse.getCapacity() - warehouse.getCurSize()) {
                player.syncClient("公会仓库容量不足");
                return;
            }

            reqLDonateItem.getDonateItems().forEach(i -> donate(backpackDb, warehouse, i));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            warehouse.unlock();
        }

        league.save();
        backpackUpdateNotifier.notify(player);
    }

    private void donate(BackpackDb backpackDb, ILWarehouse warehouse, LDonateItem donateItem) {
        ItemDb itemDb = getItem(backpackDb, donateItem.getIndex());
        backpackFacade.remove(backpackDb, donateItem.getIndex(), donateItem.getAmount());

        LeagueItemDb leagueItemDb = createLItem(itemDb, donateItem.getAmount());
        ILeagueItem ILeagueItem = leagueItemFactory.create(leagueItemDb);

        warehouse.addItem(ILeagueItem);
    }

    private void checkBackpackItem(BackpackDb backpackDb, Integer index, int amount) {
        ItemDb itemDb = getItem(backpackDb, index);
        if (itemDb.getAmount() < amount) {
            throw new GeneralErrorExecption("要捐献的背包物品数量不足");
        }
    }

    private ItemDb getItem(BackpackDb backpackDb, Integer index) {
        return backpackFacade.find(backpackDb, index)
                .orElseThrow(() -> new GeneralErrorExecption("找不到对应背包格子"));
    }

    private LeagueItemDb createLItem(ItemDb itemDb, int amout) {
        LeagueItemDb leagueItemDb = new LeagueItemDb();
        leagueItemDb.setId(idTool.generate());
        leagueItemDb.setItemUid(itemDb.getUid());
        leagueItemDb.setCfgId(itemDb.getCfgId());
        leagueItemDb.setAmount(amout);
        leagueItemDb.setExtraDb(itemDb.getExtraDb());
        return leagueItemDb;
    }
}
