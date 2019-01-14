package com.wan37.logic.player.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家注册信息返回编码
 *
 * @author linda
 */
@Service
public class PlayerRegisterResponseEncoder {

    @Autowired
    private ConfigLoader configLoader;

    public String encode(PlayerDb db) {
        String factionName = configLoader.loadName(FactionCfg.class, db.getFactionId());
        String msg = "注册成功|玩家名：%s，职业：%s （playerUid：%s）";
        return String.format(msg, db.getName(), factionName, db.getUid());
    }
}
