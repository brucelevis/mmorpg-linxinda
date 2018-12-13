package com.wan37.logic.league.service.get.item;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.LeaguePermissionEnum;
import com.wan37.logic.league.config.LeaguePositionCfg;
import com.wan37.logic.league.config.LeaguePositionCfgLoader;
import com.wan37.logic.league.database.LeagueItemDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueItem;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueGetItemExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private LeaguePositionCfgLoader leaguePositionCfgLoader;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, ReqLGetItem reqLGetItem) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        ILeagueMember me = league.getMember(player.getUid());
        LeaguePositionCfg positionCfg = leaguePositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorExecption("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(LeaguePermissionEnum.LeaguePermissionEnum_4.getId())) {
            throw new GeneralErrorExecption("没有取帮会物品的权限");
        }

        if (backpackFacade.getSpareCapacity(player) < reqLGetItem.getItems().size()) {
            throw new GeneralErrorExecption("背包剩余空间不足");
        }

        ILWarehouse warehouse = league.getWarehouse();
        try {
            warehouse.lock();
            if (!checkWarehouse(warehouse, reqLGetItem)) {
                player.syncClient("公会物品不足");
                return;
            }

            // 取公会物品格子 -> 背包物品格子
            List<LGetItem> reqItems = reqLGetItem.getItems();
            BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
            for (LGetItem item : reqItems) {
                ILeagueItem leagueItem = warehouse.getItem(item.getIndex());
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

    private boolean checkWarehouse(ILWarehouse warehouse, ReqLGetItem reqLGetItem) {
        return reqLGetItem.getItems().stream()
                .allMatch(i -> warehouse.queryItem(i.getIndex()) >= i.getAmount());
    }

    private ItemDb toItemDb(ILeagueItem leagueItem) {
        LeagueItemDb leagueItemDb = leagueItem.getLItemDb();

        ItemDb db = new ItemDb();
        db.setUid(leagueItemDb.getItemUid());
        db.setCfgId(leagueItemDb.getCfgId());
        db.setExtraDb(leagueItemDb.getExtraDb());
        return db;
    }
}
