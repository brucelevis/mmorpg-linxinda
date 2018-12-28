package com.wan37.logic.scene.schedule;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitSkillBeforeCastChecker;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.logic.summoning.ai.SummoningSkillCastTargetsGetter;
import com.wan37.logic.summoning.entity.Summoning;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneSummoningAiScheduler {

    @Autowired
    private FightingUnitSkillBeforeCastChecker fightingUnitSkillBeforeCastChecker;

    @Autowired
    private SummoningSkillCastTargetsGetter summoningSkillCastTargetsGetter;

    @Autowired
    private BehaviorManager behaviorManager;

    public void schedule(AbstractScene scene) {
        scene.getSummonings().stream()
                .filter(Summoning::isAlive)
                .forEach(s -> autoCast(s, scene));
    }

    //FIXME: 重复类似代码
    private void autoCast(Summoning summoning, AbstractScene scene) {
        ISkill skill = randSkill(summoning);
        if (skill == null) {
            return;
        }

        if (!fightingUnitSkillBeforeCastChecker.check(summoning, skill)) {
            return;
        }

        List<FightingUnit> targetList = summoningSkillCastTargetsGetter.get(summoning, skill.getSkillCfg(), scene);
        if (targetList.isEmpty()) {
            return;
        }

        // 技能生效逻辑
        SkillEffectLogicBehavior behavior = (SkillEffectLogicBehavior) behaviorManager.get(
                SkillEffectLogicBehavior.class, skill.getSkillCfg().getEffectLogic());
        behavior.behave(new SkillEffectLogicContext(summoning, skill, targetList));
    }

    private ISkill randSkill(Summoning summoning) {
        List<ISkill> skills = new ArrayList<>(summoning.getSkills().values());
        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
