package com.wan37.logic.player.service.login;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.player.encode.RespLoginPlayerDtoEncoder;
import com.wan37.logic.scene.SceneFacade;
import io.netty.channel.Channel;
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

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    public void exec(PLoginPlayer loginPlayer) {
        Long playerUid = loginPlayer.getPlayerUid();
        Channel channel = loginPlayer.getChannel();

        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        if (player == null) {
            LOG.info(ResultCode.ROLE_NOT_EXIST.getDesc());

            GeneralResponseDto dto = respLoginPlayerDtoEncoder.encode(ResultCode.ROLE_NOT_EXIST, null);
            loginPlayer.response(dto);
            return;
        }

        player.setChannel(channel);
        playerGlobalManager.addInOnlineList(player);

        //TODO: 触发登录事件

        sceneFacade.enterScene(player.getSceneId(), player);

        String msg = "登录成功|" + playerInfoEncoder.encode(player.getPlayerDb());
        player.syncClient(msg);
    }
}
