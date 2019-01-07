package com.wan37.logic.skill.cast.check;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.BuffEffectEnum;
import com.wan37.logic.buff.entity.IBuff;
import com.wan37.logic.player.Player;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 技能出手前判断条件
 */
@Service
public class FightingUnitSkillBeforeCastChecker {

    @Autowired
    private PlayerSkillBeforeCastChecker playerSkillBeforeCastChecker;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public boolean check(FightingUnit caster, ISkill skill) {
        if (isPlayer(caster)) {
            // 攻击者是玩家需要特殊处理
            Player player = (Player) caster;
            playerSkillBeforeCastChecker.check(player, skill);
        }

        AbstractScene scene = sceneActorSceneGetter.get(caster);
        if (!scene.getSceneCfg().canAttack()) {
            return throwIfIsPlayer(caster, "安全地图不能攻击");
        }

        if (!caster.isAlive()) {
            return throwIfIsPlayer(caster, "死亡状态无法攻击");
        }

        // 检查技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long interval = now - skill.getLastUseTime();
        if (interval < skill.getCdInterval()) {
            return throwIfIsPlayer(caster, "技能cd中，无法使用");
        }

        // 检查攻击者BUFF异常状态，如眩晕，封印，击飞等
        IBuff abnormality = caster.getBuffs().stream()
                .filter(b -> Objects.equals(b.getEffectId(), BuffEffectEnum.BUFF_EFFECT_4.getId()))
                .findAny()
                .orElse(null);
        if (abnormality != null) {
            return throwIfIsPlayer(caster, String.format("受%sbuff影响，无法攻击", abnormality.getName()));
        }

        return true;
    }

    private boolean throwIfIsPlayer(FightingUnit unit, String msg) {
        if (isPlayer(unit)) {
            throw new GeneralErrorException(msg);
        }

        return false;
    }

    private boolean isPlayer(FightingUnit unit) {
        return unit instanceof Player;
    }
}
