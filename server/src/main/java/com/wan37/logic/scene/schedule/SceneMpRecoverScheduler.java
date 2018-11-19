package com.wan37.logic.scene.schedule;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.Scene;
import com.wan37.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class SceneMpRecoverScheduler {

    // FIXME: 写死场景每30秒恢复1mp
    private static final int MP = 1;

    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(30);

    public void schedule(Scene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (scene.getLastRecoverMpTime() + INTERVAL >= now) {
            return;
        }

        scene.setLastRecoverMpTime(now);
        scene.getPlayers().forEach(this::recoverMp);
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

        String msg = String.format("你自动恢复了%smp", result - curMp);
        player.syncClient(msg);
    }
}
