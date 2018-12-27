package com.wan37.logic.skill.cast;

import com.google.common.collect.ImmutableList;
import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.SceneTypeEnum;
import com.wan37.logic.skill.SkillTargetTypeEnum;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import com.wan37.logic.team.entity.ITeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取人物释放技能目标对象
 */
@Service
public class PlayerSkillCastTargetsGetter {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public List<FightingUnit> get(Player caster, SkillCfg skillCfg, Long targetUid) {
        Integer targetType = skillCfg.getTargetType();
        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_1.getId())) {
            // 自己
            return ImmutableList.of(caster);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_2.getId())) {
            // 友方
            Long teamUid = caster.getTeamUid();
            if (teamUid == null) {
                return ImmutableList.of(caster);
            }

            ITeam team = teamGlobalManager.getTeam(targetUid);
            if (skillCfg.isEffectAll()) {
                // 群体技能
                return team.getMembers().stream()
                        .filter(ITeamMember::isOnline)
                        .map(m -> playerGlobalManager.getPlayerByUid(m.getPlayerUid()))
                        .collect(Collectors.toList());
            }

            if (targetUid == null) {
                throw new GeneralErrorExecption("目标不能为空");
            }

            ITeamMember teamMember = team.getMember(targetUid);
            if (teamMember == null) {
                throw new GeneralErrorExecption("找不到目标队友");
            }

            if (!teamMember.isOnline()) {
                throw new GeneralErrorExecption("目标队友离线");
            }

            Player target = playerGlobalManager.getPlayerByUid(teamMember.getPlayerUid());
            return ImmutableList.of(target);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_3.getId())) {
            // 对特定目标
            AbstractScene scene = sceneActorSceneGetter.get(caster);

            if (Objects.equals(scene.getSceneCfg().getType(), SceneTypeEnum.SCENE_TYPE_3.getId())) {
                // 在竞技场
                // FIXME: 竞技场目前只有单人决斗
                return scene.getPlayers().stream()
                        .filter(m -> !Objects.equals(m.getUid(), caster.getUid()))
                        .collect(Collectors.toList());
            }

            // 不在竞技场
            if (skillCfg.isEffectAll()) {
                // AOE 群体伤害怪物
                return new ArrayList<>(scene.getMonsters());
            }

            if (targetUid == null) {
                throw new GeneralErrorExecption("目标不能为空");
            }

            // 单一目标
            return scene.getMonsters().stream()
                    .filter(m -> Objects.equals(m.getUid(), targetUid))
                    .collect(Collectors.toList());
        }

        return ImmutableList.of();
    }
}
