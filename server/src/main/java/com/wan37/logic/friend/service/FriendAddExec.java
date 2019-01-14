package com.wan37.logic.friend.service;

import com.wan37.logic.friend.dao.FriendRequestDao;
import com.wan37.logic.friend.database.FriendRequestDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author linda
 */
@Service
public class FriendAddExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    public void exec(Player player, Long uid) {
        if (player.getPlayerDb().getFriendDb().getFriendUid().contains(uid)) {
            player.syncClient("已经是好友");
            return;
        }

        //TODO: 不可重复发送好友请求的检查

        FriendRequestDb requestDb = createRequest(player.getUid(), uid);
        Player to = playerGlobalManager.getPlayerIfPresent(uid);
        if (to != null) {
            // 对方数据在缓存，直接添加好友请求信息
            to.getPlayerDb().addFriendRequest(requestDb);

            if (playerGlobalManager.isOnline(uid)) {
                // 如果在线，推送通知
                String msg = String.format("%s对你发起了好友请求", player.getName());
                to.syncClient(msg);
            }

            afterRequest(player);
            return;
        }

        if (!playerDao.existsByUid(uid)) {
            player.syncClient("不存在的玩家uid");
            return;
        }

        friendRequestDao.save(requestDb);
        afterRequest(player);
    }

    private FriendRequestDb createRequest(Long fromPlayerUid, Long toPlayerUid) {
        FriendRequestDb db = new FriendRequestDb();
        db.setId(IdUtil.generate());
        db.setFromPlayerUid(fromPlayerUid);
        db.setTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));

        PlayerDb playerDb = new PlayerDb();
        playerDb.setUid(toPlayerUid);
        db.setPlayer(playerDb);

        return db;
    }

    private void afterRequest(Player player) {
        player.syncClient("发送添加好友请求成功");
    }
}
