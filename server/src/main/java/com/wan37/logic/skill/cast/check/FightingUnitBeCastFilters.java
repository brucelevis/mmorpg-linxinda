package com.wan37.logic.skill.cast.check;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.base.SceneActor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 被当做目标的检查
 *
 * @author linda
 */
@Service
public class FightingUnitBeCastFilters {

    public List<FightingUnit> filter(FightingUnit caster, List<FightingUnit> targets) {
        // TODO：这里还要检查是否有无敌Buff，无法选中等状态
        return targets.stream()
                .filter(SceneActor::isAlive)
                .filter(u -> Objects.equals(u.getSceneId(), caster.getSceneId()))
                .collect(Collectors.toList());
    }
}
