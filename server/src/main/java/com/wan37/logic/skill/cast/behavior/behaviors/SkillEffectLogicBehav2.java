package com.wan37.logic.skill.cast.behavior.behaviors;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.team.TeamGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @see com.wan37.logic.skill.SkillEffectLogicEnum#SKILL_EFFECT_LOGIC_2
 */
@Service
class SkillEffectLogicBehav2 implements SkillEffectLogicBehavior {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void behave(SkillEffectLogicContext context) {

    }

//    private List<FightingUnit> getTargetList(Player player, ISkill skill, Long targetUid) {
//        Integer targetType = skill.getSkillCfg().getTargetType();
//        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_1.getId())) {
//            return ImmutableList.of(player);
//        }
//
//        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_2.getId())) {
//            Long teamUid = player.getTeamUid();
//            if (teamUid == null) {
//                return ImmutableList.of(player);
//            }
//
//            return teamGlobalManager.getTeam(targetUid).getMembers().stream()
//                    .filter(ITeamMember::isOnline)
//                    .map(m -> playerGlobalManager.getPlayerByUid(m.getPlayerUid()))
//                    .collect(Collectors.toList());
//        }
//
//        AbstractScene scene = sceneActorSceneGetter.get(player);
//        if (skill.getSkillCfg().isEffectAll()) {
//            return new ArrayList<>(scene.getMonsters());
//        }
//
//        if (targetUid == null) {
//            throw new GeneralErrorExecption("目标uid不能为空");
//        }
//
//        return scene.getMonsters().stream()
//                .filter(m -> Objects.equals(m.getUid(), targetUid))
//                .collect(Collectors.toList());
//    }
}