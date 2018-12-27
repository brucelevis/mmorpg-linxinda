package com.wan37.logic.buff.rand;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.BuffTargetEnum;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.config.BuffCfgLoader;
import com.wan37.logic.player.service.FightingUnitBuffAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.config.SkillBuffCfg;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SkillBuffRandomer {

    @Autowired
    private BuffCfgLoader buffCfgLoader;

    @Autowired
    private IBuff.Factory buffFactory;

    @Autowired
    private FightingUnitBuffAdder fightingUnitBuffAdder;

    public void rand(FightingUnit attacker, FightingUnit target, SkillBuffCfg cfg, AbstractScene scene) {
        if (!RandomUtil.isHit(cfg.getProbability())) {
            // 没触发buff
            return;
        }

        BuffCfg buffCfg = buffCfgLoader.load(cfg.getId()).orElse(null);
        if (buffCfg == null) {
            return;
        }

        IBuff buff = buffFactory.create(buffCfg);
        if (Objects.equals(buff.getTarget(), BuffTargetEnum.BUFF_TARGET_1.getId())) {
            // 对自己施加buff
            fightingUnitBuffAdder.add(attacker, buff);

            String msg = String.format("[%s]触发了[%s]的效果", attacker.getName(), buff.getName());
            scene.getPlayers().forEach(p -> p.syncClient(msg));
        } else {
            // 对目标施加buff
            if (target.isAlive()) {
                fightingUnitBuffAdder.add(target, buff);

                String msg = String.format("[%s]对[%s]施加了[%s]的效果", attacker.getName(), target.getName(), buff.getName());
                scene.getPlayers().forEach(p -> p.syncClient(msg));
            }
        }
    }
}
