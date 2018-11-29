package com.wan37.logic.dungeon.service.enter;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonCfgLoader;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonEnterExec {

    @Autowired
    private DungeonCfgLoader dungeonCfgLoader;

    public void exec(Player player, Integer dungeonId) {
        DungeonCfg dungeonCfg = dungeonCfgLoader.load(dungeonId).orElse(null);
        if (dungeonCfg == null) {
            throw new GeneralErrorExecption("找不到相应的副本");
        }


    }
}
