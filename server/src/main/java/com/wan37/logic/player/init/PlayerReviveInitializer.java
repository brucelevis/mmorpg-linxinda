package com.wan37.logic.player.init;

import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

/**
 * 玩家复活初始化
 *
 * @author linda
 */
@Service
public class PlayerReviveInitializer {

    public void init(Player player) {
        player.setHp(player.getMaxHp());
        player.setMp(player.getMaxMp());
        player.setAlive(true);
    }
}
