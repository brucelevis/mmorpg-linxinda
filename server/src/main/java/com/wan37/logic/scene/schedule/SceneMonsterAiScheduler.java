package com.wan37.logic.scene.schedule;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.ai.MonsterSkillCastTargetsGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
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
public class SceneMonsterAiScheduler {

    @Autowired
    private MonsterSkillCastTargetsGetter monsterSkillCastTargetsGetter;

    @Autowired
    private BehaviorManager behaviorManager;

    @Autowired
    private FightingUnitSkillBeforeCastChecker fightingUnitSkillBeforeCastChecker;

    public void schedule(AbstractScene scene) {
        scene.getMonsters().stream()
                .filter(Monster::isAlive)
                .forEach(m -> autoAttack(m, scene));
    }

    private void autoAttack(Monster monster, AbstractScene scene) {
        ISkill skill = randSkill(monster);
        if (skill == null) {
            return;
        }

        if (!fightingUnitSkillBeforeCastChecker.check(monster, skill)) {
            return;
        }

        List<FightingUnit> targetList = monsterSkillCastTargetsGetter.get(monster, skill.getSkillCfg(), scene);
        if (targetList.isEmpty()) {
            return;
        }

        // 技能生效逻辑
        SkillEffectLogicBehavior behavior = (SkillEffectLogicBehavior) behaviorManager.get(
                SkillEffectLogicBehavior.class, skill.getSkillCfg().getEffectLogic());
        behavior.behave(new SkillEffectLogicContext(monster, skill, targetList));
    }

    private ISkill randSkill(Monster monster) {
        List<ISkill> skills = new ArrayList<>(monster.getSkills().values());
        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
