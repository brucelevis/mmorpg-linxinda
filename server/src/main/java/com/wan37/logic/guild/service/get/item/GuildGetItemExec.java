package com.wan37.logic.guild.service.get.item;

import com.wan37.config.ConfigLoader;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildItem;
import com.wan37.logic.guild.entity.GuildMember;
import com.wan37.logic.guild.entity.GuildWarehouse;
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
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("未加入公会");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        GuildMember me = league.getMember(player.getUid());
        GuildPositionCfg positionCfg = configLoader.load(GuildPositionCfg.class, me.getPosition())
                .orElseThrow(() -> new GeneralErrorException("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_4.getId())) {
            throw new GeneralErrorException("没有取帮会物品的权限");
        }

        if (backpackFacade.getSpareCapacity(player) < reqGuildGetItem.getItems().size()) {
            throw new GeneralErrorException("背包剩余空间不足");
        }

        GuildWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
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
            warehouse.unlock();
        }

        league.save();
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
