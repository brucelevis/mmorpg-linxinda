package com.wan37.logic.player.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.faction.config.FactionCfgLoader;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.PlayerRegisterResponseEncoder;
import com.wan37.logic.player.init.PlayerDbInitializer;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.util.IdUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
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

    public void exec(Integer factionId, String name, Channel channel) {
        PlayerDb playerDb = createPlayerDb(factionId, name);
        playerDbInitializer.init(playerDb);

        String msg = playerRegisterResponseEncoder.encode(playerDb);
        channel.writeAndFlush(msg + "\n");
    }

    private PlayerDb createPlayerDb(Integer factionId, String name) {
        PlayerDb db = new PlayerDb();
        db.setUid(IdUtil.generate());
        db.setName(name);
        db.setLevel(1);

        factionCfgLoader.load(factionId)
                .orElseThrow(() -> new GeneralErrorException("找不到职业导表，错误的职业id"));
        db.setFactionId(factionId);

        db.setSceneId(sceneCfgLoader.loadDefault()
                .map(SceneCfg::getId)
                .orElse(0));

        return db;
    }
}
