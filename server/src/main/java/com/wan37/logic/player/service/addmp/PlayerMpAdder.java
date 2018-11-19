package com.wan37.logic.player.service.addmp;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.stereotype.Service;

@Service
public class PlayerMpAdder {

    public void add(PlayerDb playerDb, int addMp) {
        int cur = playerDb.getMp();
        int mp = cur + addMp;

        PAttrDb mpDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_MP.getId());
        if (mpDb == null) {
            return;
        }

        int max = (int) Math.round(mpDb.getValue());
        int result = max > mp ? mp : max;
        if (result == cur) {
            // 没变化
            return;
        }

        playerDb.setMp(result);
    }
}
