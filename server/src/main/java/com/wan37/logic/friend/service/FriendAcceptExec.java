package com.wan37.logic.friend.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.FriendAddEvent;
import com.wan37.logic.friend.database.FriendDb;
import com.wan37.logic.friend.database.FriendRequestDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class FriendAcceptExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Long id) {
        PlayerDb playerDb = player.getPlayerDb();
        FriendRequestDb requestDb = findRequest(playerDb, id);
        if (requestDb == null) {
            player.syncClient("找不到好友请求id");
            return;
        }

        Long fromUid = requestDb.getFromPlayerUid();
        FriendDb friendDb = playerDb.getFriendDb();
        if (friendDb.getFriendUid().contains(fromUid)) {
            rmRequest(playerDb, fromUid);
            player.syncClient("已经是好友");
            return;
        }

        friendDb.getFriendUid().add(fromUid);
        rmRequest(playerDb, fromUid);
        player.syncClient("添加好友成功");

        //FIXME: 先简单实现，把玩家Load出来
        Player from = playerGlobalManager.getPlayerByUid(fromUid);
        from.getPlayerDb().getFriendDb().getFriendUid().add(player.getUid());

        // 如果对方在线
        if (playerGlobalManager.isOnline(fromUid)) {
            from.syncClient(String.format("%s接受了你的好友请求", player.getName()));
        }

        generalEventListenersManager.fireEvent(new FriendAddEvent(from));
        generalEventListenersManager.fireEvent(new FriendAddEvent(player));
    }

    private FriendRequestDb findRequest(PlayerDb playerDb, Long id) {
        return playerDb.getRequestList().stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findAny()
                .orElse(null);
    }

    private void rmRequest(PlayerDb playerDb, Long fromUid) {
        playerDb.setRequestList(playerDb.getRequestList().stream()
                .filter(r -> !Objects.equals(r.getFromPlayerUid(), fromUid))
                .collect(Collectors.toSet()));
    }
}
