package com.wan37.logic.summoning.listener;

import com.wan37.event.entity.DieEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.summoning.Summoning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
class SummoningOnDie implements GeneralEventListener<DieEvent> {

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Override
    public void execute(DieEvent dieEvent) {
        FightingUnit unit = dieEvent.getUnit();
        if (!(unit instanceof Summoning)) {
            return;
        }

        Summoning summoning = (Summoning) unit;
        AbstractScene scene = sceneActorSceneGetter.get(summoning);
        scene.setSummonings(scene.getSummonings().stream()
                .filter(s -> !Objects.equals(s.getUid(), summoning.getUid()))
                .collect(Collectors.toList()));
    }
}
