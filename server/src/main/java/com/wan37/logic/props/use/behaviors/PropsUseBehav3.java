package com.wan37.logic.props.use.behaviors;

import com.google.common.base.Objects;
import com.wan37.logic.buff.entity.IBuff;
import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.config.BuffCfgLoader;
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
class PropsUseBehav3 implements PropsUseBehavior {

    @Autowired
    private BuffCfgLoader buffCfgLoader;

    @Autowired
    private IBuff.Factory IBuffFactory;

    @Override
    public void behave(PropsUseContext context) {
        PropsCfg propsCfg = context.getPropsCfg();
        Integer buffId = Integer.parseInt(propsCfg.getUseLogicArgs());

        BuffCfg buffCfg = buffCfgLoader.load(buffId).orElse(null);
        if (buffCfg == null) {
            return;
        }

        Player player = context.getPlayer();
        IBuff buff = IBuffFactory.create(buffCfg);

        // 去重
        List<IBuff> buffs = player.getBuffs();
        buffs.stream().filter(b -> Objects.equal(b.getId(), buff.getId()))
                .findAny()
                .ifPresent(buffs::remove);

        buffs.add(buff);
    }
}
