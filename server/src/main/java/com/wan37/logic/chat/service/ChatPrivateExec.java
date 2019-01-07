package com.wan37.logic.chat.service;

import com.wan37.logic.chat.ChatTypeEnum;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class ChatPrivateExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player from, Long toUid, String msg) {
        if (!playerGlobalManager.isOnline(toUid)) {
            from.syncClient("对方不在线");
            return;
        }

        Player to = playerGlobalManager.getPlayerByUid(toUid);

        String content = String.format("【%s】 [%s]：%s", ChatTypeEnum.CHAT_TYPE_PRIVATE.getName(), from.getName(), msg);
        to.syncClient(content);
    }
}
