package com.wan37.logic.scene.schedule;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.monster.init.MonsterInitializer;
import com.wan37.logic.scene.Scene;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SceneScheduler {

    @Autowired
    private MonsterInitializer monsterInitializer;

    @Autowired
    private MonsterEncoder monsterEncoder;

    public void schedule(Scene scene) {
        // 需要刷新的怪物
        List<Monster> monsterList = scene.getMonsters().stream()
                .filter(m -> !m.isAlive())
                .filter(this::canRefresh)
                .collect(Collectors.toList());

        if (monsterList.isEmpty()) {
            // 没有需要刷新的怪物
            return;
        }

        monsterList.forEach(m -> monsterInitializer.init(m));

        // 怪物更新推送
        String msg = encode(monsterList);
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }

    private boolean canRefresh(Monster monster) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        return now >= monster.getDeadTime() + monster.getMonsterCfg().getCreateCd();
    }

    private String encode(List<Monster> monsterList) {
        String head = "场景怪物刷新通知:\n";
        String monsters = monsterList.stream()
                .map(m -> monsterEncoder.encode(m))
                .collect(Collectors.joining("\n"));

        return head + monsters;
    }
}
