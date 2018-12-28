package com.wan37.logic.skill.cast.ai.target;

import com.google.common.collect.ImmutableList;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.summoning.Summoning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FightingUnitSkillCastTargetsGetter {

    @Autowired
    private MonsterSkillCastTargetsGetter monsterSkillCastTargetsGetter;

    @Autowired
    private SummoningSkillCastTargetsGetter summoningSkillCastTargetsGetter;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public List<FightingUnit> get(FightingUnit unit, SkillCfg skillCfg) {
        AbstractScene scene = sceneActorSceneGetter.get(unit);
        if (unit instanceof Monster) {
            Monster monster = (Monster) unit;
            return monsterSkillCastTargetsGetter.get(monster, skillCfg, scene);
        }

        if (unit instanceof Summoning) {
            Summoning summoning = (Summoning) unit;
            return summoningSkillCastTargetsGetter.get(summoning, skillCfg, scene);
        }

        return ImmutableList.of();
    }
}
