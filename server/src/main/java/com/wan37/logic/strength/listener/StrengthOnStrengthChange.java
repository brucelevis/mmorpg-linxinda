package com.wan37.logic.strength.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.StrengthChangeEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.strength.encode.PlayerStrengthInfoEncoder;
import com.wan37.logic.strength.init.PlayerStrengthDbRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 面板战力需要变化监听
 */
@Service
class StrengthOnStrengthChange implements GeneralEventListener<StrengthChangeEvent> {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private PlayerStrengthDbRefresher playerStrengthDbRefresher;

    @Autowired
    private PlayerStrengthInfoEncoder playerStrengthInfoEncoder;

    @Override
    public void execute(StrengthChangeEvent strengthChangeEvent) {
        Long uid = strengthChangeEvent.getPlayerUid();
        Player player = playerGlobalManager.getPlayerByUid(uid);
        if (player == null) {
            return;
        }

        // 计算战力变化
        PlayerDb playerDb = player.getPlayerDb();
        playerStrengthDbRefresher.refresh(playerDb);

        playerDao.save(playerDb);

        // 推送最新面板伤害变化
        String msg = "面板总伤害更新通知|" + playerStrengthInfoEncoder.encode(playerDb.getPlayerStrengthDb());
        player.syncClient(msg);
    }
}
