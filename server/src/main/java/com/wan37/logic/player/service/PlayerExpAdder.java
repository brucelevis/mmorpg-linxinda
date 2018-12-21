package com.wan37.logic.player.service;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.LevelUpEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.config.ExpCfg;
import com.wan37.logic.player.config.ExpCfgLoader;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerExpAdder {

    @Autowired
    private ExpCfgLoader expCfgLoader;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void add(Player player, long exp) {
        List<ExpCfg> cfgList = expCfgLoader.loads().stream()
                .filter(c -> player.getLevel() <= c.getId())
                .sorted(Comparator.comparingInt(ExpCfg::getId))
                .collect(Collectors.toList());

        AbstractScene scene = sceneActorSceneGetter.get(player);
        for (int i = 0; i < cfgList.size() && exp > 0; i++) {
            long curExp = player.getExp();
            ExpCfg expCfg = cfgList.get(i);
            if (curExp + exp < expCfg.getExp()) {
                player.setExp(curExp + exp);
                return;
            }

            player.setlevel(player.getLevel() + 1);
            player.setExp(0);
            exp -= expCfg.getExp() - curExp;

            // 升级
            String msg = String.format("升级通知|%s升到了%s级", player.getName(), player.getLevel());
            scene.getPlayers().forEach(p -> p.syncClient(msg));

            genernalEventListenersManager.fireEvent(new LevelUpEvent(player));
        }
    }
}

