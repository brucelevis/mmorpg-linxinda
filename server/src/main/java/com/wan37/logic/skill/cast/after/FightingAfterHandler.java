package com.wan37.logic.skill.cast.after;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.rand.SkillBuffRandomHandler;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 攻击后处理器
 *
 * @author linda
 */
@Service
public class FightingAfterHandler {

    @Autowired
    private SkillBuffRandomHandler skillBuffRandomHandler;

    public void handle(FightingUnit attacker, FightingUnit target, Skill skill, AbstractScene scene) {
        // 概率触发Buff
        skill.getSkillCfg().getBuffs().forEach(c -> skillBuffRandomHandler.handle(attacker, target, c, scene));
    }
}
