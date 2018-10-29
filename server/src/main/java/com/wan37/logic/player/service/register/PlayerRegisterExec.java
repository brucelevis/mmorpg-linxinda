package com.wan37.logic.player.service.register;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.RespRegisterPlayerDtoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerRegisterExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private RespRegisterPlayerDtoEncoder respRegisterPlayerDtoEncoder;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private Player.Factory factory;

    public void exec(PRegisterPlayer regPlayer) {
        PlayerDb playerDb = createPlayerDb(regPlayer);
        playerDao.save(playerDb);

        Player player = factory.create(playerDb, regPlayer.getChannel());
        playerGlobalManager.add(player);

        GeneralResponseDto dto = respRegisterPlayerDtoEncoder.encode(ResultCode.SUCESS, player.getUid());
        regPlayer.response(dto);
    }

    private PlayerDb createPlayerDb(PRegisterPlayer regPlayer) {
        PlayerDb db = new PlayerDb();
        db.setUid(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        db.setName(regPlayer.getName());
        db.setFactionId(regPlayer.getFactionId());
        return db;
    }
}
