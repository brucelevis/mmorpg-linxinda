package com.wan37.logic.friend.service;

import com.wan37.logic.friend.dao.FriendDao;
import com.wan37.logic.friend.database.FriendDb;
import com.wan37.logic.friend.database.FriendRequestInfoDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class FriendAddExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private FriendDao friendDao;

    public void exec(Player player, Long uid) {
        if (playerGlobalManager.isOnline(uid)) {

            afterRequest(player);
            return;
        }

        FriendDb friendDb = friendDao.getByPlayerUid(uid);
        friendDb.getFriendRequestDb().getRequestList().add(createRequest(player.getUid()));
        friendDao.save(friendDb);

        afterRequest(player);
    }

    private FriendRequestInfoDb createRequest(Long playerUid) {
        FriendRequestInfoDb db = new FriendRequestInfoDb();
        db.setPlayerUid(playerUid);

        long expireTime = DateTimeUtils.toEpochMilli(LocalDateTime.now()) + TimeUnit.DAYS.toMillis(3);
        db.setExpireTime(expireTime);
        return db;
    }

    private void afterRequest(Player player) {
        player.syncClient("发送添加好友请求成功");
    }
}
