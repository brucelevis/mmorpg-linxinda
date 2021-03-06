package com.wan37.logic.friend.service;

import com.wan37.logic.friend.database.FriendRequestDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class FriendRejectExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerDao playerDao;

    public void exec(Player player, Long id) {
        PlayerDb playerDb = player.getPlayerDb();
        FriendRequestDb requestDb = findRequest(playerDb, id);
        if (requestDb == null) {
            player.syncClient("找不到好友请求id");
            return;
        }

        Long fromUid = requestDb.getFromPlayerUid();
        rmRequest(playerDb, fromUid);
        player.syncClient(String.format("你拒绝了%s的好友请求", playerDao.find(fromUid)));

        // 如果对方在线
        if (playerGlobalManager.isOnline(fromUid)) {
            Player from = playerGlobalManager.getPlayerByUid(fromUid);
            from.syncClient(String.format("%s拒绝了你的好友请求", player.getName()));
        }
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
