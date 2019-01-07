package com.wan37.logic.player.service.register;

import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.PlayerRegisterResponseEncoder;
import com.wan37.logic.player.init.PlayerDbInitializer;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerRegisterExec {

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private PlayerDbInitializer playerDbInitializer;

    @Autowired
    private FactionCfgLoader factionCfgLoader;

    @Autowired
    private PlayerRegisterResponseEncoder playerRegisterResponseEncoder;

    public void exec(PRegisterPlayer regPlayer) {
        PlayerDb playerDb = createPlayerDb(regPlayer);
        playerDbInitializer.init(playerDb);

        String msg = playerRegisterResponseEncoder.encode(playerDb);
        regPlayer.response(msg);
    }

    private PlayerDb createPlayerDb(PRegisterPlayer regPlayer) {
        PlayerDb db = new PlayerDb();
        db.setUid(IdUtil.generate());
        db.setName(regPlayer.getName());
        db.setLevel(1);

        Integer factionId = regPlayer.getFactionId();
        factionCfgLoader.load(factionId).orElseThrow(() -> new RuntimeException("找不到职业导表，错误的职业id"));
        db.setFactionId(regPlayer.getFactionId());

        db.setSceneId(sceneCfgLoader.loadDefault()
                .map(SceneCfg::getId)
                .orElse(0));

        return db;
    }
}
