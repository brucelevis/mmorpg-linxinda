package com.wan37.logic.player.service.register;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
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

    public void exec(PRegisterPlayer regPlayer) {
        // FIXME: 未持久化

        Player player = getPlayer(regPlayer);
        playerGlobalManager.add(player);

        GeneralResponseDto dto = respRegisterPlayerDtoEncoder.encode(ResultCode.SUCESS, player.getUid());
        regPlayer.response(dto);
    }

    @Deprecated
    private Player getPlayer(PRegisterPlayer regPlayer) {
        Player p = new Player();

        p.setUid(UUID.randomUUID().getMostSignificantBits());
        p.setName(regPlayer.getName());
        p.setFactionId(regPlayer.getFactionId());
        p.setLevel(1);
        p.setChannel(regPlayer.getChannel());

        return p;
    }
}
