package com.wan37.logic.player.init;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PlayerDbInitializer {

    @Autowired
    private PlayerDao playerDao;

    public void init(PlayerDb playerDb) {
        initBackPack(playerDb);

        playerDao.save(playerDb);
    }

    private void initBackPack(PlayerDb playerDb) {
        BackpackDb backpackDb = playerDb.getBackpackDb();
        if (backpackDb != null) {
            return;
        }

        BackpackDb newDb = new BackpackDb();
        newDb.setCapacity(20);
        newDb.setItemMap(new HashMap<>());

        playerDb.setBackpackDb(newDb);
    }
}
