package com.wan37.logic.guild.service.donate.item;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.entity.GuildItem;
import com.wan37.logic.guild.entity.GuildWarehouse;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.player.Player;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildDonateItemExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private GuildItem.Factory leagueItemFactory;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, ReqGuildDonateItem reqGuildDonateItem) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        reqGuildDonateItem.getDonateItems().forEach(i -> checkBackpackItem(backpackDb, i.getIndex(), i.getAmount()));

        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        GuildWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            if (reqGuildDonateItem.getDonateItems().size() > warehouse.getCapacity() - warehouse.getCurSize()) {
                player.syncClient("公会仓库容量不足");
                return;
            }

            reqGuildDonateItem.getDonateItems().forEach(i -> donate(backpackDb, warehouse, i));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            warehouse.unlock();
        }

        league.save();
        backpackUpdateNotifier.notify(player);
    }

    private void donate(BackpackDb backpackDb, GuildWarehouse warehouse, GuildDonateItem donateItem) {
        ItemDb itemDb = getItem(backpackDb, donateItem.getIndex());
        backpackFacade.remove(backpackDb, donateItem.getIndex(), donateItem.getAmount());

        GuildItemDb guildItemDb = createLItem(itemDb, donateItem.getAmount());
        GuildItem guildItem = leagueItemFactory.create(guildItemDb);

        warehouse.addItem(guildItem);
    }

    private void checkBackpackItem(BackpackDb backpackDb, Integer index, int amount) {
        ItemDb itemDb = getItem(backpackDb, index);
        if (itemDb.getAmount() < amount) {
            throw new GeneralErrorException("要捐献的背包物品数量不足");
        }
    }

    private ItemDb getItem(BackpackDb backpackDb, Integer index) {
        return backpackFacade.find(backpackDb, index)
                .orElseThrow(() -> new GeneralErrorException("找不到对应背包格子"));
    }

    private GuildItemDb createLItem(ItemDb itemDb, int amount) {
        GuildItemDb guildItemDb = new GuildItemDb();
        guildItemDb.setId(IdUtil.generate());
        guildItemDb.setItemUid(itemDb.getUid());
        guildItemDb.setCfgId(itemDb.getCfgId());
        guildItemDb.setAmount(amount);
        guildItemDb.setExtraDb(itemDb.getExtraDb());
        return guildItemDb;
    }
}
