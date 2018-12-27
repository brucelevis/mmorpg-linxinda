package com.wan37.logic.skill.cast.after;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.rand.SkillBuffRandomer;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.entity.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightingAfterHandler {

    @Autowired
    private SkillBuffRandomer skillBuffRandomer;

    public void handle(FightingUnit attacker, FightingUnit target, ISkill skill, AbstractScene scene) {
        // 概率触发Buff
        skill.getSkillCfg().getBuffs().forEach(c -> skillBuffRandomer.rand(attacker, target, c, scene));
    }
}
