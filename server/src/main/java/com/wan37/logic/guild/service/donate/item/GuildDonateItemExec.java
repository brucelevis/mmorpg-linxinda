package com.wan37.logic.guild.service.donate.item;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildItem;
import com.wan37.logic.guild.entity.GuildWarehouse;
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

        // 检查背包物品
        for (GuildDonateItem item : reqGuildDonateItem.getDonateItems()) {
            ItemDb itemDb = backpackFacade.find(backpackDb, item.getIndex()).orElse(null);
            if (itemDb == null) {
                player.syncClient("找不到对应背包格子");
                return;
            }

            if (itemDb.getAmount() < item.getAmount()) {
                player.syncClient("要捐献的背包物品数量不足");
                return;
            }
        }

        if (player.getGuildUid() == null) {
            player.syncClient("未加入公会");
            return;
        }

        Guild guild = guildGlobalManager.get(player.getGuildUid());
        GuildWarehouse warehouse = guild.getWarehouse();
        try {
            guild.lock();
            if (reqGuildDonateItem.getDonateItems().size() > warehouse.getCapacity() - warehouse.getCurSize()) {
                player.syncClient("公会仓库容量不足");
                return;
            }

            reqGuildDonateItem.getDonateItems().forEach(i -> donate(backpackDb, warehouse, i));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            guild.unlock();
        }

        guild.save();
        backpackUpdateNotifier.notify(player);
    }

    private void donate(BackpackDb backpackDb, GuildWarehouse warehouse, GuildDonateItem donateItem) {
        ItemDb itemDb = backpackFacade.find(backpackDb, donateItem.getIndex()).get();
        backpackFacade.remove(backpackDb, donateItem.getIndex(), donateItem.getAmount());

        GuildItemDb guildItemDb = createLItem(itemDb, donateItem.getAmount());
        GuildItem guildItem = leagueItemFactory.create(guildItemDb);

        warehouse.addItem(guildItem);
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
