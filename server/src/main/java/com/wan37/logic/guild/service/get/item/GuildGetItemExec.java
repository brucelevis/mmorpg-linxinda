package com.wan37.logic.guild.service.get.item;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.guild.GuildItem;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.guild.GuildWarehouse;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linda
 */
@Service
public class GuildGetItemExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, ReqGuildGetItem reqGuildGetItem) {
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

        if (backpackFacade.getSpareCapacity(player) < reqGuildGetItem.getItems().size()) {
            player.syncClient("背包剩余空间不足");
            return;
        }

        GuildWarehouse warehouse = guild.getWarehouse();
        try {
            guild.lock();
            if (!checkWarehouse(warehouse, reqGuildGetItem)) {
                player.syncClient("公会物品不足");
                return;
            }

            // 取公会物品格子 -> 背包物品格子
            List<GuildGetItem> reqItems = reqGuildGetItem.getItems();
            BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
            for (GuildGetItem item : reqItems) {
                GuildItem leagueItem = warehouse.getItem(item.getIndex());
                ItemDb itemDb = toItemDb(leagueItem);
                itemDb.setAmount(item.getAmount());
                backpackFacade.add(backpackDb, itemDb);
            }

            reqItems.forEach(i -> warehouse.rmItem(i.getIndex(), i.getAmount()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            guild.unlock();
        }

        guild.save();
        backpackUpdateNotifier.notify(player);
    }

    private boolean checkWarehouse(GuildWarehouse warehouse, ReqGuildGetItem reqGuildGetItem) {
        return reqGuildGetItem.getItems().stream()
                .allMatch(i -> warehouse.queryItem(i.getIndex()) >= i.getAmount());
    }

    private ItemDb toItemDb(GuildItem leagueItem) {
        GuildItemDb guildItemDb = leagueItem.getGuildItemDb();

        ItemDb db = new ItemDb();
        db.setUid(guildItemDb.getItemUid());
        db.setCfgId(guildItemDb.getCfgId());
        db.setExtraDb(guildItemDb.getExtraDb());
        return db;
    }
}
