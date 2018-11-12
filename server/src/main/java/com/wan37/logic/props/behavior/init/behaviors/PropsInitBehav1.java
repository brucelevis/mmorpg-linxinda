package com.wan37.logic.props.behavior.init.behaviors;

import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.EquipCfgLoader;
import com.wan37.logic.equipment.config.EquipInitAttrCfg;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.props.behavior.init.PropsInitBehavior;
import com.wan37.logic.props.behavior.init.PropsInitContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 装备物品初始化
 */
@Service
class PropsInitBehav1 implements PropsInitBehavior {

    private static final Logger LOG = Logger.getLogger(PropsInitBehav1.class);

    @Autowired
    private EquipCfgLoader equipCfgLoader;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

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
        extraDb.setDurabilityv(70);

        extraDb.setBaseAttrs(equipCfg.getAttrs().stream()
                .map(this::createAttr)
                .collect(Collectors.toList()));

        context.setExtraDb(extraDb);
    }

    private EquipAttrDb createAttr(EquipInitAttrCfg cfg) {
        EquipAttrDb db = new EquipAttrDb();
        db.setCfgId(cfg.getAttrCfgId());
        db.setValue(cfg.getValue());

        db.setName(attrCfgLoader.load(cfg.getAttrCfgId())
                .map(AttrCfg::getName)
                .orElse(null));

        return db;
    }
}
