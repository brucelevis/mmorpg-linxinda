package com.wan37.logic.skill.service;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.cast.ai.target.PlayerSkillCastTargetsGetter;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitSkillBeforeCastChecker;
import com.wan37.logic.skill.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linda
 */
@Service
public class SkillCastExec {

    @Autowired
    private BehaviorManager behaviorManager;

    @Autowired
    private FightingUnitSkillBeforeCastChecker fightingUnitSkillBeforeCastChecker;

    @Autowired
    private PlayerSkillCastTargetsGetter playerSkillCastTargetsGetter;

    public void exec(Player caster, Integer skillId, Long targetUid) {
        Skill skill = caster.getSkills().get(skillId);
        if (skill == null) {
            caster.syncClient("找不到目标技能");
            return;
        }

        if (!fightingUnitSkillBeforeCastChecker.check(caster, skill)) {
            return;
        }

        // 根据作用目标类型找出目标
        List<FightingUnit> targetList = playerSkillCastTargetsGetter.get(caster, skill.getSkillCfg(), targetUid);
        if (targetList.isEmpty()) {
            caster.syncClient("找不到技能施放目标");
            return;
        }

        // 技能生效逻辑
        SkillEffectLogicBehavior behavior = (SkillEffectLogicBehavior) behaviorManager.get(
                SkillEffectLogicBehavior.class, skill.getSkillCfg().getEffectLogic());
        behavior.behave(new SkillEffectLogicContext(caster, skill, targetList));
    }
}
