package com.wan37.logic.backpack.service.item;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackItemInfoEncoder;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class BackpackItemInfoExec {

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private BackpackItemInfoEncoder backpackItemInfoEncoder;

    public void exec(Player player, long uid) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackFacade.find(backpackDb, uid).orElse(null);
        if (itemDb == null) {
            player.syncClient("找不到该物品");
            return;
        }

        String msg = backpackItemInfoEncoder.encode(itemDb);
        player.syncClient(msg);
    }
}
