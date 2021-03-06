package com.wan37.logic.player.init;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PlayerEachAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.init.PlayerAttrDbInitializer;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.friend.database.FriendDb;
import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.database.PlayerSkillDb;
import com.wan37.logic.skill.init.PlayerSkillDbInitializer;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import com.wan37.logic.strength.init.PlayerStrengthDbRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 初始化玩家数据
 *
 * @author linda
 */
@Service
public class PlayerDbInitializer {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerAttrDbInitializer playerAttrDbInitializer;

    @Autowired
    private PlayerStrengthDbRefresher playerStrengthDbRefresher;

    @Autowired
    private PlayerSkillDbInitializer playerSkillDbInitializer;

    public void init(PlayerDb playerDb) {
        initBackpack(playerDb);
        initCurrency(playerDb);
        initAttrs(playerDb);
        initEquip(playerDb);
        initPlayer(playerDb);
        initSkills(playerDb);
        initPlayerStrength(playerDb);
        initFriend(playerDb);
        initMission(playerDb);

        playerDao.save(playerDb);
    }

    private void initBackpack(PlayerDb playerDb) {
        BackpackDb backpackDb = playerDb.getBackpackDb();
        if (backpackDb != null) {
            return;
        }

        BackpackDb newDb = new BackpackDb();
        //FIXME: 先写死初始容量，后改成配置表配置
        newDb.setCapacity(10);
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
        Map<Integer, PlayerEachAttrDb> attrs = playerDb.getPlayerAttrDb().getAttrs();

        PlayerEachAttrDb hpDb = attrs.get(AttrEnum.HP.getId());
        int hp = hpDb != null ? (int) Math.round(hpDb.getValue()) : 0;
        playerDb.setHp(hp);

        PlayerEachAttrDb mpDb = attrs.get(AttrEnum.MP.getId());
        int mp = mpDb != null ? (int) Math.round(mpDb.getValue()) : 0;
        playerDb.setMp(mp);

        playerDb.setAlive(true);
    }

    private void initPlayerStrength(PlayerDb playerDb) {
        PlayerStrengthDb playerStrengthDb = playerDb.getPlayerStrengthDb();
        if (playerStrengthDb != null) {
            return;
        }

        playerStrengthDbRefresher.refresh(playerDb);
    }

    private void initSkills(PlayerDb playerDb) {
        PlayerSkillDb playerSkillDb = playerDb.getPlayerSkillDb();
        if (playerSkillDb != null) {
            return;
        }

        PlayerSkillDb newDb = new PlayerSkillDb();
        playerSkillDbInitializer.init(newDb, playerDb.getFactionId());

        playerDb.setPlayerSkillDb(newDb);
    }

    private void initFriend(PlayerDb playerDb) {
        FriendDb friendDb = playerDb.getFriendDb();
        if (friendDb != null) {
            return;
        }

        FriendDb newDb = new FriendDb();
        newDb.setId(playerDb.getUid());
        newDb.setFriendUid(new HashSet<>());
        newDb.setPlayer(playerDb);

        playerDb.setFriendDb(newDb);
    }

    private void initMission(PlayerDb playerDb) {
        MissionDb missionDb = playerDb.getMissionDb();
        if (missionDb != null) {
            return;
        }

        MissionDb newDb = new MissionDb();
        newDb.setId(playerDb.getUid());
        newDb.setMissions(new HashSet<>());

        playerDb.setMissionDb(newDb);
    }
}
