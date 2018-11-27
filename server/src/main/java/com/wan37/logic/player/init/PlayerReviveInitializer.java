package com.wan37.logic.player.init;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerReviveInitializer {

    public void init(PlayerDb playerDb) {
        Map<Integer, PAttrDb> attrs = playerDb.getPlayerAttrDb().getAttrs();

        PAttrDb hpDb = attrs.get(AttrEnum.ATTR_HP.getId());
        int hp = hpDb != null ? (int) Math.round(hpDb.getValue()) : 0;
        playerDb.setHp(hp);

        PAttrDb mpDb = attrs.get(AttrEnum.ATTR_MP.getId());
        int mp = mpDb != null ? (int) Math.round(mpDb.getValue()) : 0;
        playerDb.setMp(mp);

        playerDb.setAlive(true);
    }
}
