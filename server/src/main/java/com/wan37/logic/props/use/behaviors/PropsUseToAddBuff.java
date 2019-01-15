package com.wan37.logic.props.use.behaviors;

import com.google.common.base.Objects;
import com.wan37.behavior.BehaviorLogic;
import com.wan37.config.ConfigLoader;
import com.wan37.logic.buff.Buff;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.use.PropsUseBehavior;
import com.wan37.logic.props.use.PropsUseContext;
import com.wan37.logic.props.config.PropsCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消耗物品产生Buff类
 */
@Service
@BehaviorLogic(id = 3)
class PropsUseToAddBuff implements PropsUseBehavior {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private Buff.Factory buffFactory;

    @Override
    public void behave(PropsUseContext context) {
        PropsCfg propsCfg = context.getPropsCfg();
        Integer buffId = Integer.parseInt(propsCfg.getUseLogicArgs());

        BuffCfg buffCfg = configLoader.load(BuffCfg.class, buffId).orElse(null);
        if (buffCfg == null) {
            return;
        }

        Player player = context.getPlayer();
        Buff buff = buffFactory.create(buffCfg);

        // 去重
        List<Buff> buffs = player.getBuffs();
        buffs.stream().filter(b -> Objects.equal(b.getId(), buff.getId()))
                .findAny()
                .ifPresent(buffs::remove);

        buffs.add(buff);
    }
}
