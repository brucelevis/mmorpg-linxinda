package com.wan37.logic.player.encode;

import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Deprecated
@Service
public class PlayerInfoEncoder {

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    public String encode(PlayerDb db) {
        String factionName = factionCfgLoader.load(db.getFactionId())
                .map(FactionCfg::getName)
                .orElse("");

        String sceneName = sceneCfgLoader.load(db.getSceneId())
                .map(SceneCfg::getName)
                .orElse("");

        String msg = "名字：%s，Hp: %s，Mp：%s，Exp: %s，职业：%s，等级：%s，当前所在场景：%s  (playerUid：%s)";
        return String.format(msg, db.getName(), db.getHp(), db.getMp(), db.getExp(), factionName, db.getLevel(), sceneName, db.getUid());
    }
}
