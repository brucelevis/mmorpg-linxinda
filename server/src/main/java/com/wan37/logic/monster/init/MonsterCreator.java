package com.wan37.logic.monster.init;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterInitAttrCfg;
import com.wan37.logic.monster.config.MonsterInitSkillCfg;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 生成怪物逻辑
 *
 * @author linda
 */
@Service
public class MonsterCreator {

    @Autowired
    private MonsterInitializer monsterInitializer;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    @Autowired
    private Skill.Factory iSkillFactory;

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    public Monster create(MonsterCfg cfg, AbstractScene scene) {
        Monster monster = new Monster();
        monster.setUid(IdUtil.generate());
        monster.setMonsterCfg(cfg);
        monster.setSceneId(scene.getId());
        monster.setSceneUid(scene.getUid());

        monster.setSkills(cfg.getSkills().stream()
                .map(this::createSkill)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Skill::getId, Function.identity())));

        monster.setAttrs(cfg.getAttrs().stream()
                .collect(Collectors.toMap(MonsterInitAttrCfg::getId, MonsterInitAttrCfg::getValue)));

        monsterInitializer.init(monster);
        calcStrength(monster);

        return monster;
    }

    private Skill createSkill(MonsterInitSkillCfg cfg) {
        SkillCfg skillCfg = skillCfgLoader.load(cfg.getId()).orElse(null);
        if (skillCfg == null) {
            return null;
        }

        return iSkillFactory.create(skillCfg, cfg.getLevel());
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
        //FIXME: 重复代码
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
