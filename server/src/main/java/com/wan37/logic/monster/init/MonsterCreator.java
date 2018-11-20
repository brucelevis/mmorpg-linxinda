package com.wan37.logic.monster.init;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterInitAttrCfg;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MonsterCreator {

    @Autowired
    private IdTool idTool;

    @Autowired
    private MonsterInitializer monsterInitializer;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public Monster create(MonsterCfg cfg) {
        Monster monster = new Monster();
        monster.setUid(idTool.generate());
        monster.setMonsterCfg(cfg);

        monster.setAttrs(cfg.getAttrs().stream()
                .collect(Collectors.toMap(MonsterInitAttrCfg::getId, MonsterInitAttrCfg::getValue)));

        monsterInitializer.init(monster);
        calcStrength(monster);

        return monster;
    }

    private void calcStrength(Monster monster) {
        List<Pair> pairList = monster.getAttrs().entrySet().stream()
                .map(this::toPair)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        monster.setBaseAttackVal((int) Math.round(pairList.stream()
                .mapToDouble(p -> p.val * p.attrCfg.getBaseAttackValue())
                .sum()));

        monster.setBaseDefenseVal((int) Math.round(pairList.stream()
                .mapToDouble(p -> p.val * p.attrCfg.getBaseDefenseValue())
                .sum()));
    }

    private Pair toPair(Map.Entry<Integer, Double> entry) {
        AttrCfg attrCfg = attrCfgLoader.load(entry.getKey()).orElse(null);
        if (attrCfg == null) {
            return null;
        }

        Pair pair = new Pair();
        pair.attrCfg = attrCfg;
        pair.val = entry.getValue();
        return pair;
    }

    private static class Pair {

        AttrCfg attrCfg;

        double val;
    }
}
