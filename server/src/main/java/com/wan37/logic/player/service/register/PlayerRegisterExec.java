package com.wan37.logic.player.service.register;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.encode.RespRegisterPlayerDtoEncoder;
import com.wan37.logic.player.init.PlayerCreator;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerRegisterExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private RespRegisterPlayerDtoEncoder respRegisterPlayerDtoEncoder;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private PlayerCreator playerCreator;

    @Autowired
    private IdTool idTool;

    public void exec(PRegisterPlayer regPlayer) {
        PlayerDb playerDb = createPlayerDb(regPlayer);
        playerDao.save(playerDb);

        Player player = playerCreator.create(playerDb, regPlayer.getChannel());
        playerGlobalManager.add(player);

        GeneralResponseDto dto = respRegisterPlayerDtoEncoder.encode(ResultCode.REGISTER_SUCCESS, player.getUid());
        regPlayer.response(dto);
    }

    private PlayerDb createPlayerDb(PRegisterPlayer regPlayer) {
        PlayerDb db = new PlayerDb();
        db.setUid(idTool.generate());
        db.setName(regPlayer.getName());
        db.setFactionId(regPlayer.getFactionId());
        db.setLevel(1);

        db.setSceneId(sceneCfgLoader.loadDefault()
                .map(SceneCfg::getId)
                .orElse(0));

        return db;
    }
}
