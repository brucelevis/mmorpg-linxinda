package com.wan37.logic.props.init.behaviors;

import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipInitAttrCfg;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.props.init.PropsInitBehavior;
import com.wan37.logic.props.init.PropsInitContext;
import com.wan37.util.RandomUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 1：装备物品初始化
 */
@Service
class PropsInitBehavior1 implements PropsInitBehavior {

    private static final Logger LOG = Logger.getLogger(PropsInitBehavior1.class);

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    @Override
    public void behave(PropsInitContext context) {
        Integer cfgId = context.getPropsCfg().getId();
        EquipCfg equipCfg = equipCfgLoader.load(cfgId).orElse(null);
        if (equipCfg == null) {
            LOG.info("找不到装备配置表，初始化失败");
            return;
        }

        EquipExtraDb extraDb = new EquipExtraDb();

        //FIXME: 初始耐久度先写死
        extraDb.setDurability(100);

        extraDb.setBaseAttrs(equipCfg.getAttrs().stream()
                .map(this::createAttr)
                .collect(Collectors.toList()));

        context.setExtraDb(extraDb);
    }

    private EquipAttrDb createAttr(EquipInitAttrCfg cfg) {
        EquipAttrDb db = new EquipAttrDb();
        db.setCfgId(cfg.getAttrCfgId());
        db.setValue(cfg.getBaseValue() + cfg.getStep() * RandomUtil.rand(cfg.getMaxRandInt()));
        return db;
    }
}
