package com.wan37.logic.player.service;

import com.wan37.config.ConfigLoader;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.LevelUpEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.config.ExpCfg;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增加经验
 *
 * @author linda
 */
@Service
public class PlayerExpAdder {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void add(Player player, long exp) {
        player.syncClient(String.format("获得%s经验", exp));

        List<ExpCfg> cfgList = configLoader.loads(ExpCfg.class).stream()
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

            player.setLevel(player.getLevel() + 1);
            player.setExp(0);
            exp -= expCfg.getExp() - curExp;

            // 升级
            String msg = String.format("升级通知|%s升到了%s级", player.getName(), player.getLevel());
            scene.getPlayers().forEach(p -> p.syncClient(msg));

            generalEventListenersManager.fireEvent(new LevelUpEvent(player));
        }
    }
}

