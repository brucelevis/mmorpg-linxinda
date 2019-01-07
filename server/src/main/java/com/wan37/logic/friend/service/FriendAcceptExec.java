package com.wan37.logic.friend.service;

import com.wan37.event.entity.FriendAddEvent;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.friend.database.FriendDb;
import com.wan37.logic.friend.database.FriendRequestDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FriendAcceptExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Long id) {
        PlayerDb playerDb = player.getPlayerDb();
        FriendRequestDb requestDb = playerDb.getRequestList().stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findAny()
                .orElseThrow(() -> new GeneralErrorException("找不到好友请求id"));

        Long fromUid = requestDb.getFromPlayerUid();
        FriendDb friendDb = playerDb.getFriendDb();
        if (friendDb.getFriendUids().contains(fromUid)) {
            rmRequest(playerDb, fromUid);
            player.syncClient("已经是好友");
            return;
        }

        friendDb.getFriendUids().add(fromUid);
        rmRequest(playerDb, fromUid);
        player.syncClient("添加好友成功");

        //FIXME: 先简单实现，把玩家Load出来
        Player from = playerGlobalManager.getPlayerByUid(fromUid);
        from.getPlayerDb().getFriendDb().getFriendUids().add(player.getUid());

        // 如果对方在线
        if (playerGlobalManager.isOnline(fromUid)) {
            from.syncClient(String.format("%s接受了你的好友请求", player.getName()));
        }

        generalEventListenersManager.fireEvent(new FriendAddEvent(from));
        generalEventListenersManager.fireEvent(new FriendAddEvent(player));
    }

    public void rmRequest(PlayerDb playerDb, Long fromUid) {
        playerDb.setRequestList(playerDb.getRequestList().stream()
                .filter(r -> !Objects.equals(r.getFromPlayerUid(), fromUid))
                .collect(Collectors.toSet()));
    }
}
