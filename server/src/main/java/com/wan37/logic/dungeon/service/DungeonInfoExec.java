package com.wan37.logic.dungeon.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.dungeon.config.*;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.VirtualItemCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class DungeonInfoExec {

    @Autowired
    private ConfigLoader configLoader;

    public void exec(Player player) {
        String head = "副本信息如下：\n";
        String content = configLoader.loads(DungeonCfg.class).stream()
                .map(this::encodeDungeon)
                .collect(Collectors.joining("\n------------------------------------------------------------------------\n"));

        player.syncClient(head + content);
    }

    private String encodeDungeon(DungeonCfg cfg) {
        String msg = String.format("主题（id：%s）：%s （%s分钟）|要求：%s人以上\n描述：%s",
                cfg.getId(), cfg.getName(), cfg.getLimitTime(), cfg.getLimitNum(), cfg.getDesc());

        String monsters = "消灭：" + cfg.getMonsters().values().stream()
                .map(DungeonMonsterGroupCfg::getMonsters)
                .flatMap(Collection::stream)
                .map(this::encodeMonster)
                .collect(Collectors.joining("，"));

        String reward = "有机会获得奖励：" + cfg.getReward().stream()
                .map(this::encodeReward)
                .collect(Collectors.joining("，"));

        return msg + "\n"
                + monsters + "\n"
                + reward;
    }

    private String encodeMonster(DungeonMonsterCfg cfg) {
        return configLoader.loadName(MonsterCfg.class, cfg.getMonsterId());
    }

    private String encodeReward(DungeonRewardCfg cfg) {
        String name = cfg.getId() < 200 ? configLoader.loadName(VirtualItemCfg.class, cfg.getId()) :
                configLoader.loadName(PropsCfg.class, cfg.getId());
        return String.format("%s×%s", name, cfg.getAmount());
    }
}
