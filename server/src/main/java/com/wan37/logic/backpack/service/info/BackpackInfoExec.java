package com.wan37.logic.backpack.service.info;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.encode.RespBackpackInfoDtoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BackpackInfoExec {

    private static final Logger LOG = Logger.getLogger(BackpackInfoExec.class);

    @Autowired
    private RespBackpackInfoDtoEncoder respBackpackInfoDtoEncoder;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Channel channel) {
        String channelId = channel.id().asLongText();
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        GeneralResponseDto dto = respBackpackInfoDtoEncoder.encode(ResultCode.BACKPACK_INFO, backpackDb);
        player.syncClient(dto);
    }
}
