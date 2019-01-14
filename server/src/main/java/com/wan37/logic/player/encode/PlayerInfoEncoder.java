package com.wan37.logic.player.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家信息编码
 *
 * @author linda
 */
@Service
public class PlayerInfoEncoder {

    @Autowired
    private ConfigLoader configLoader;

    public String encode(Player player) {
        String factionName = configLoader.loadName(FactionCfg.class, player.getFactionId());
        String sceneName = configLoader.loadName(SceneCfg.class, player.getSceneId());

        String msg = "名字：%s，Hp: %s，Mp：%s，Exp: %s，存活：%s，职业：%s，等级：%s，当前所在场景：%s  (playerUid：%s)";
        return String.format(msg, player.getName(), player.getHp(), player.getMp(), player.getExp(),
                player.isAlive(), factionName, player.getLevel(), sceneName, player.getUid());
    }
}
