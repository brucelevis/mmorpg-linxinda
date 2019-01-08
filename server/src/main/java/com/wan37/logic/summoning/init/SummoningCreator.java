package com.wan37.logic.summoning.init;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.config.SkillCfgLoader;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.logic.summoning.config.SummoningCfg;
import com.wan37.logic.summoning.config.SummoningInitAttrCfg;
import com.wan37.logic.summoning.config.SummoningInitSkillCfg;
import com.wan37.logic.summoning.Summoning;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 召唤兽生成器
 *
 * @author linda
 */
@Service
public class SummoningCreator {

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    @Autowired
    private Skill.Factory iSkillFactory;

    @Autowired
    private SkillCfgLoader skillCfgLoader;

    public Summoning create(Long masterUid, SummoningCfg cfg, AbstractScene scene) {
        Summoning summoning = new Summoning();
        summoning.setUid(IdUtil.generate());
        summoning.setSummoningCfg(cfg);
        summoning.setBelongUid(masterUid);
        summoning.setSceneId(scene.getId());
        summoning.setSceneUid(scene.getUid());

        summoning.setSkills(cfg.getInitSkills().stream()
                .map(this::createSkill)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Skill::getId, Function.identity())));

        summoning.setAttrs(cfg.getInitAttrs().stream()
                .collect(Collectors.toMap(SummoningInitAttrCfg::getId, SummoningInitAttrCfg::getValue)));

        summoning.setAlive(true);
        summoning.setHp(summoning.getMaxHp());
        summoning.setMp(summoning.getMaxMp());
        calcStrength(summoning);
        return summoning;
    }

    private Skill createSkill(SummoningInitSkillCfg cfg) {
        SkillCfg skillCfg = skillCfgLoader.load(cfg.getId()).orElse(null);
        if (skillCfg == null) {
            return null;
        }

        return iSkillFactory.create(skillCfg, cfg.getLevel());
    }

    private void calcStrength(Summoning summoning) {
        List<Pair> pairList = summoning.getAttrs().entrySet().stream()
                .map(this::toPair)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        summoning.setBaseAttackVal((int) Math.round(pairList.stream()
                .mapToDouble(p -> p.val * p.attrCfg.getBaseAttackValue())
                .sum()));

        summoning.setBaseDefenseVal((int) Math.round(pairList.stream()
                .mapToDouble(p -> p.val * p.attrCfg.getBaseDefenseValue())
                .sum()));
    }

    //FIXME：代码重复
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
