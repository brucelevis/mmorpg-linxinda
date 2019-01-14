package com.wan37.logic.player.service;

import com.wan37.config.ConfigLoader;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.PlayerRegisterResponseEncoder;
import com.wan37.logic.player.init.PlayerDbInitializer;
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
    private ConfigLoader configLoader;

    @Autowired
    private PlayerDbInitializer playerDbInitializer;

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

        configLoader.load(FactionCfg.class, factionId)
                .orElseThrow(() -> new GeneralErrorException("找不到职业导表，错误的职业id"));
        db.setFactionId(factionId);

        //FIXME: 写死默认安全村地图id 1000
        db.setSceneId(1000);
        return db;
    }
}
