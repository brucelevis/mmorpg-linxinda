package com.wan37.logic.scene.schedule;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneMpRecoverScheduler {

    // FIXME: 写死场景每10秒恢复5mp
    private static final int MP = 5;

    private static final int INTERVAL = 11;

    @Autowired
    private PlayerDao playerDao;

    public void schedule(Scene scene) {
        int mpCounter = (scene.getRecoverMpCounter() + 1) % INTERVAL;
        if (mpCounter == INTERVAL - 1) {
            scene.getPlayers().forEach(this::recoverMp);
        }

        scene.setRecoverMpCounter(mpCounter);
    }

    private void recoverMp(Player player) {
        PlayerDb playerDb = player.getPlayerDb();
        PAttrDb attrDb = playerDb.getPlayerAttrDb().getAttrs().get(AttrEnum.ATTR_MP.getId());
        if (attrDb == null) {
            return;
        }

        int curMp = playerDb.getMp();
        int maxMp = (int) Math.round(attrDb.getValue());
        if (curMp == maxMp) {
            return;
        }

        int result = curMp + MP > maxMp ? maxMp : curMp + MP;
        playerDb.setMp(result);
        playerDao.save(playerDb);

        String msg = String.format("你自动恢复了%smp", result - curMp);
        player.syncClient(msg);
    }
}
