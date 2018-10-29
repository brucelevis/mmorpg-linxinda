package com.wan37.logic.player.service.login;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.encode.RespLoginPlayerDtoEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerLoginExec {

    private static final Logger LOG = Logger.getLogger(PlayerLoginExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private RespLoginPlayerDtoEncoder respLoginPlayerDtoEncoder;

    public void exec(PLoginPlayer loginPlayer) {
        Player player = playerGlobalManager.getPlayerByUid(loginPlayer.getPlayerUid(), loginPlayer.getChannel());
        if (player == null) {
            LOG.info(ResultCode.ROLE_NOT_EXIST.getDesc());

            GeneralResponseDto dto = respLoginPlayerDtoEncoder.encode(ResultCode.ROLE_NOT_EXIST, player);
            loginPlayer.response(dto);
            return;
        }

        GeneralResponseDto dto = respLoginPlayerDtoEncoder.encode(ResultCode.LOGIN_SUCCESS, player);
        loginPlayer.response(dto);
    }
}
