package com.wan37.logic.player.init;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.init.PlayerAttrDbInitializer;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.init.PlayerStrengthDbRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerDbInitializer {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerAttrDbInitializer playerAttrDbInitializer;

    @Autowired
    private PlayerStrengthDbRefresher playerStrengthDbRefresher;

    public void init(PlayerDb playerDb) {
        initBackpack(playerDb);
        initCurrency(playerDb);
        initAttrs(playerDb);
        initEquip(playerDb);
        initPlayer(playerDb);
        initPlayerStrength(playerDb);

        playerDao.save(playerDb);
    }

    private void initBackpack(PlayerDb playerDb) {
        BackpackDb backpackDb = playerDb.getBackpackDb();
        if (backpackDb != null) {
            return;
        }

        BackpackDb newDb = new BackpackDb();
        //FIXME: 先写死初始容量，后改成配置表配置
        newDb.setCapacity(20);
        newDb.setItemMap(new HashMap<>());

        playerDb.setBackpackDb(newDb);
    }

    private void initCurrency(PlayerDb playerDb) {
        CurrencyDb currencyDb = playerDb.getCurrencyDb();
        if (currencyDb != null) {
            return;
        }

        CurrencyDb newDb = new CurrencyDb();
        newDb.setItemMap(new HashMap<>());

        playerDb.setCurrencyDb(newDb);
    }

    private void initAttrs(PlayerDb playerDb) {
        PlayerAttrDb playerAttrDb = playerDb.getPlayerAttrDb();
        if (playerAttrDb != null) {
            return;
        }

        PlayerAttrDb newDb = new PlayerAttrDb();
        playerAttrDbInitializer.init(newDb, playerDb.getFactionId());

        playerDb.setPlayerAttrDb(newDb);
    }

    private void initEquip(PlayerDb playerDb) {
        EquipDb equipDb = playerDb.getEquipDb();
        if (equipDb != null) {
            return;
        }

        EquipDb newDb = new EquipDb();
        newDb.setItems(new HashMap<>());

        playerDb.setEquipDb(newDb);
    }

    private void initPlayer(PlayerDb playerDb) {
        Map<Integer, PAttrDb> attrs = playerDb.getPlayerAttrDb().getAttrs();

        PAttrDb hpDb = attrs.get(AttrEnum.ATTR_HP.getId());
        double hp = hpDb != null ? hpDb.getValue() : 0;
        playerDb.setHp(hp);

        PAttrDb mpDb = attrs.get(AttrEnum.ATTR_MP.getId());
        double mp = mpDb != null ? mpDb.getValue() : 0;
        playerDb.setMp(mp);
    }

    private void initPlayerStrength(PlayerDb playerDb) {
        PlayerStrengthDb playerStrengthDb = playerDb.getPlayerStrengthDb();
        if (playerStrengthDb != null) {
            return;
        }

        playerStrengthDbRefresher.refresh(playerDb);
    }
}
