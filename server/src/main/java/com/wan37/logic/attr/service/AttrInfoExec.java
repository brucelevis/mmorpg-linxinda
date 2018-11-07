package com.wan37.logic.attr.service;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.attr.encode.RespPlayerAttrDtoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttrInfoExec {

    private static final Logger LOG = Logger.getLogger(AttrInfoExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private RespPlayerAttrDtoEncoder respPlayerAttrDtoEncoder;

    public void exec(Channel channel) {
        String channelId = channel.id().asLongText();
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            LOG.info("角色未登录，不允许发送该命令");
            return;
        }

        PlayerAttrDb playerAttrDb = player.getPlayerDb().getPlayerAttrDb();
        GeneralResponseDto dto = respPlayerAttrDtoEncoder.encode(ResultCode.ATTR_INFO, playerAttrDb);
        player.syncClient(dto);
    }
}
