package com.wan37.logic.friend.service;

import com.wan37.logic.friend.database.FriendRequestDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class FriendInfoExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerDao playerDao;

    public void exec(Player player) {
        PlayerDb playerDb = player.getPlayerDb();

        String friendHead = "好友列表如下：\n";
        String friendList = playerDb.getFriendDb().getFriendUid().stream()
                .map(this::encodeFriend)
                .collect(Collectors.joining("\n"));

        String requestHead = "好友请求列表如下：\n";
        String requestList = playerDb.getRequestList().stream()
                .map(this::encodeRequest)
                .collect(Collectors.joining("\n"));

        player.syncClient(friendHead + friendList + "\n" + requestHead + requestList);
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

    private String encodeRequest(FriendRequestDb requestDb) {
        Long fromUid = requestDb.getFromPlayerUid();
        Player from = playerGlobalManager.getPlayerIfPresent(fromUid);
        String fromName = from != null ? from.getName() : playerDao.find(fromUid);

        return String.format("%s向你发起了好友请求（id：%s）", fromName, requestDb.getId());
    }
}
