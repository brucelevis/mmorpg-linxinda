package com.wan37.logic.friend.service;

import com.wan37.logic.friend.database.FriendDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class FriendInfoExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerDao playerDao;

    public void exec(Player player) {
        FriendDb friendDb = player.getPlayerDb().getFriendDb();

        String head = "好友列表如下：\n";
        String content = friendDb.getFriendUids().stream()
                .map(this::encodeFriend)
                .collect(Collectors.joining("\n"));

        player.syncClient(head + content);
    }

    private String encodeFriend(Long uid) {
        if (playerGlobalManager.isOnline(uid)) {
            Player player = playerGlobalManager.getPlayerByUid(uid);
            return encodePlayer(player.getName(), true);
        }

        PlayerDb playerDb = playerDao.getByUid(uid);
        return encodePlayer(playerDb.getName(), false);
    }

    private String encodePlayer(String name, boolean isOnline) {
        return String.format("%s（%s）", name, isOnline ? "在线" : "离线");
    }
}
