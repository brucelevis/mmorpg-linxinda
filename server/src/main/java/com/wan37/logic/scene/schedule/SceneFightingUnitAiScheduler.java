package com.wan37.logic.scene.schedule;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.base.SceneActor;
import com.wan37.logic.skill.cast.ai.target.FightingUnitSkillCastTargetsGetter;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitSkillBeforeCastChecker;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneFightingUnitAiScheduler {

    @Autowired
    private FightingUnitSkillBeforeCastChecker fightingUnitSkillBeforeCastChecker;

    @Autowired
    private FightingUnitSkillCastTargetsGetter fightingUnitSkillCastTargetsGetter;

    @Autowired
    private BehaviorManager behaviorManager;

    public void schedule(AbstractScene scene) {
        scene.getAllUnit().stream()
                .filter(this::isNotPlayer)
                .filter(SceneActor::isAlive)
                .forEach(this::autoCastSkill);
    }

    private boolean isNotPlayer(FightingUnit unit) {
        return !(unit instanceof Player);
    }

    private void autoCastSkill(FightingUnit unit) {
        ISkill skill = randSkill(unit);
        if (skill == null) {
            return;
        }

        if (!fightingUnitSkillBeforeCastChecker.check(unit, skill)) {
            return;
        }

        List<FightingUnit> targetList = fightingUnitSkillCastTargetsGetter.get(unit, skill.getSkillCfg());
        if (targetList.isEmpty()) {
            return;
        }

        // 技能生效逻辑
        SkillEffectLogicBehavior behavior = (SkillEffectLogicBehavior) behaviorManager.get(
                SkillEffectLogicBehavior.class, skill.getSkillCfg().getEffectLogic());
        behavior.behave(new SkillEffectLogicContext(unit, skill, targetList));
    }

    private ISkill randSkill(FightingUnit unit) {
        List<ISkill> skills = new ArrayList<>(unit.getSkills().values());
        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
